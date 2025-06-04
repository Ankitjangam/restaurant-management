package com.restaurant.restaurant_management.service;

import com.restaurant.restaurant_management.dto.OrderResponse;
import com.restaurant.restaurant_management.dto.PlaceOrderRequest;
import com.restaurant.restaurant_management.model.*;
import com.restaurant.restaurant_management.repository.MenuItemRepository;
import com.restaurant.restaurant_management.repository.OrderRepository;
import com.restaurant.restaurant_management.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;

    @Override
    @Transactional
    public Order placeOrder(PlaceOrderRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow();

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setUser(user);

        List<OrderItem> orderItems = request.getItems().stream().map(itemDTO -> {
            MenuItem menuItem = menuItemRepository.findById(itemDTO.getMenuItemId())
                    .orElseThrow(() -> new RuntimeException("Menu item not found"));

            OrderItem item = new OrderItem();
            item.setMenuItem(menuItem);
            item.setQuantity(itemDTO.getQuantity());
            item.setPrice(menuItem.getPrice());
            item.setOrder(order);
            return item;
        }).collect(Collectors.toList());

        double totalAmount = orderItems.stream().mapToDouble(i -> i.getQuantity() * i.getPrice()).sum();
        order.setTotalAmount(totalAmount);
        order.setOrderItems(orderItems);

        return orderRepository.save(order);
    }


    @Override
    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return toResponse(order);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> getOrdersByStatus(String status) {
        OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
        return orderRepository.findByStatus(orderStatus).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> getOrdersForCustomer(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findByUser(user).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId, String username) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized to cancel this order");
        }
        if (order.getStatus() == OrderStatus.COMPLETED) {
            throw new RuntimeException("Cannot cancel a completed order.");
        }
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }


    private OrderResponse toResponse(Order order) {
        return new OrderResponse(order.getId(), order.getOrderDate(), order.getStatus().name(), order.getTotalAmount());
    }
}

