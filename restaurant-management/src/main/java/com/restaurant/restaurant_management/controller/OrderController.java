package com.restaurant.restaurant_management.controller;

import com.restaurant.restaurant_management.dto.OrderResponse;
import com.restaurant.restaurant_management.dto.PlaceOrderRequest;
import com.restaurant.restaurant_management.model.Order;
import com.restaurant.restaurant_management.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * REST controller for managing orders.
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * Place a new order.
     *
     * @param request order request data
     * @return created Order entity
     */
    @PostMapping("/place")
    public ResponseEntity<Order> placeOrder(@RequestBody PlaceOrderRequest request) {
        Order order = orderService.placeOrder(request);
        return ResponseEntity.ok(order);
    }

    /**
     * Get order details by order ID.
     *
     * @param orderId order identifier
     * @return order response DTO
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    /**
     * Get all orders.
     *
     * @return list of all orders
     */
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    /**
     * Get orders filtered by status.
     *
     * @param status order status
     * @return list of orders with the given status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderResponse>> getOrdersByStatus(@PathVariable String status) {
        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
    }

    /**
     * Get orders of the currently authenticated user.
     *
     * @param authentication authentication object containing user details
     * @return list of orders for the authenticated user
     */
    @GetMapping("/my")
    public ResponseEntity<List<OrderResponse>> getMyOrders(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(orderService.getOrdersForCustomer(username));
    }

    /**
     * Update status of an order.
     *
     * @param orderId order identifier
     * @param status  new status to update
     * @return success message
     */
    @PutMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok("Order status updated successfully");
    }

    /**
     * Cancel an order by the authenticated user.
     *
     * @param orderId        order identifier
     * @param authentication authentication object containing user details
     * @return success message
     */
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId, Authentication authentication) {
        String username = authentication.getName();
        orderService.cancelOrder(orderId, username);
        return ResponseEntity.ok("Order cancelled successfully");
    }

    /**
     * Filter orders by optional parameters.
     *
     * @param userId    filter by user ID
     * @param username  filter by username
     * @param startDate filter by start date (ISO)
     * @param endDate   filter by end date (ISO)
     * @param status    filter by order status
     * @return filtered list of orders
     */
    @GetMapping("/filter")
    public ResponseEntity<List<OrderResponse>> filterOrders(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String status) {

        List<OrderResponse> filteredOrders = orderService.filterOrders(userId, username, startDate, endDate, status);
        return ResponseEntity.ok(filteredOrders);
    }

    /**
     * Get filtered orders for admin panel.
     *
     * @param username  filter by username
     * @param status    filter by status
     * @param startDate filter by start date (String)
     * @param endDate   filter by end date (String)
     * @return list of filtered orders for admin
     */
    @GetMapping("/admin/orders")
    public ResponseEntity<List<OrderResponse>> getOrdersForAdmin(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {

        List<OrderResponse> orders = orderService.getFilteredOrders(username, status, startDate, endDate);
        return ResponseEntity.ok(orders);
    }
}
