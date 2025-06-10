package com.restaurant.restaurant_management.serviceImp;

import com.restaurant.restaurant_management.dto.OrderResponse;
import com.restaurant.restaurant_management.dto.PlaceOrderRequest;
import com.restaurant.restaurant_management.exception.InvalidRequestException;
import com.restaurant.restaurant_management.exception.ResourceNotFoundException;
import com.restaurant.restaurant_management.exception.UnauthorizedActionException;
import com.restaurant.restaurant_management.model.*;
import com.restaurant.restaurant_management.repository.InventoryRepository;
import com.restaurant.restaurant_management.repository.MenuItemRepository;
import com.restaurant.restaurant_management.repository.OrderRepository;
import com.restaurant.restaurant_management.repository.UserRepository;
import com.restaurant.restaurant_management.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;
    private final InventoryRepository inventoryRepository;

    /**
     * Places a new order for the currently authenticated user.
     *
     * @param request the order request details
     * @return the placed Order entity
     */
    @Override
    @Transactional
    public Order placeOrder(PlaceOrderRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setUser(user);

        // FIXED here: use itemDTO variable, NOT the class name
        List<OrderItem> orderItems = request.getItems().stream()
            .map(itemDTO -> createOrderItemWithInventoryCheck(itemDTO, order))
            .collect(Collectors.toList());

        double totalAmount = orderItems.stream()
            .mapToDouble(i -> i.getQuantity() * i.getPrice())
            .sum();
        order.setTotalAmount(totalAmount);
        order.setOrderItems(orderItems);

        return orderRepository.save(order);
    }

    private OrderItem createOrderItemWithInventoryCheck(PlaceOrderRequest.OrderItemDTO itemDTO, Order order) {
        MenuItem menuItem = menuItemRepository.findById(itemDTO.getMenuItemId())
            .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + itemDTO.getMenuItemId()));

        Inventory inventory = inventoryRepository.findByItemName(menuItem.getName())
            .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for item: " + menuItem.getName()));

        if (inventory.getQuantity() < itemDTO.getQuantity()) {
            throw new InvalidRequestException("Insufficient inventory for item: " + menuItem.getName());
        }

        inventory.setQuantity(inventory.getQuantity() - itemDTO.getQuantity());
        inventoryRepository.save(inventory);

        OrderItem item = new OrderItem();
        item.setMenuItem(menuItem);
        item.setQuantity(itemDTO.getQuantity());
        item.setPrice(menuItem.getPrice());
        item.setOrder(order);
        return item;
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        return toResponse(order);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> getOrdersByStatus(String status) {
        OrderStatus orderStatus = parseOrderStatus(status);
        return orderRepository.findByStatus(orderStatus).stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> getOrdersForCustomer(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return orderRepository.findByUser(user).stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        order.setStatus(parseOrderStatus(status));
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId, String username) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        if (!order.getUser().getUsername().equals(username)) {
            throw new UnauthorizedActionException("Unauthorized to cancel this order");
        }
        if (order.getStatus() == OrderStatus.COMPLETED) {
            throw new InvalidRequestException("Cannot cancel a completed order.");
        }
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Override
    public List<OrderResponse> filterOrders(Long userId, String username, LocalDate startDate, LocalDate endDate, String status) {
        User user = null;
        if (userId != null) {
            user = userRepository.findById(userId).orElse(null);
        } else if (username != null) {
            user = userRepository.findByUsername(username).orElse(null);
        }

        OrderStatus orderStatus = null;
        if (status != null && !status.isEmpty()) {
            orderStatus = parseOrderStatus(status);
        }

        LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = endDate != null ? endDate.atTime(LocalTime.MAX) : null;

        List<Order> filteredOrders = orderRepository.filterOrders(user, orderStatus, startDateTime, endDateTime);

        return filteredOrders.stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> getFilteredOrders(String username, String status, String startDate, String endDate) {
        LocalDateTime start = startDate != null ? LocalDateTime.parse(startDate) : LocalDateTime.MIN;
        LocalDateTime end = endDate != null ? LocalDateTime.parse(endDate) : LocalDateTime.MAX;

        List<Order> orders;

        if (username != null && status != null) {
            OrderStatus orderStatus = parseOrderStatus(status);
            orders = orderRepository.findByUserUsernameAndStatusAndOrderDateBetween(username, orderStatus, start, end);
        } else if (username != null) {
            orders = orderRepository.findByUserUsernameAndOrderDateBetween(username, start, end);
        } else if (status != null) {
            OrderStatus orderStatus = parseOrderStatus(status);
            orders = orderRepository.findByStatusAndOrderDateBetween(orderStatus, start, end);
        } else {
            orders = orderRepository.findByOrderDateBetween(start, end);
        }

        return orders.stream().map(this::toResponse).collect(Collectors.toList());
    }

    private OrderStatus parseOrderStatus(String status) {
        try {
            return OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("Invalid order status: " + status);
        }
    }

    private OrderResponse toResponse(Order order) {
        return new OrderResponse(order.getId(), order.getOrderDate(), order.getStatus().name(), order.getTotalAmount());
    }
}
