package com.restaurant.restaurant_management.service;

import com.restaurant.restaurant_management.dto.OrderResponse;
import com.restaurant.restaurant_management.dto.PlaceOrderRequest;
import com.restaurant.restaurant_management.model.Order;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for managing orders.
 */
public interface OrderService {

    /**
     * Places a new order.
     *
     * @param request the place order request DTO
     * @return the created Order entity
     */
    Order placeOrder(PlaceOrderRequest request);

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId the order ID
     * @return the order response DTO
     */
    OrderResponse getOrderById(Long orderId);

    /**
     * Retrieves all orders.
     *
     * @return list of order response DTOs
     */
    List<OrderResponse> getAllOrders();

    /**
     * Retrieves orders by status.
     *
     * @param status the order status (e.g., "PENDING", "CONFIRMED")
     * @return list of order response DTOs matching the status
     */
    List<OrderResponse> getOrdersByStatus(String status);

    /**
     * Retrieves orders placed by a specific customer.
     *
     * @param username the customer's username
     * @return list of order response DTOs for the customer
     */
    List<OrderResponse> getOrdersForCustomer(String username);

    /**
     * Updates the status of an order.
     *
     * @param orderId the order ID
     * @param status  the new status
     */
    void updateOrderStatus(Long orderId, String status);

    /**
     * Cancels an order if it belongs to the given user.
     *
     * @param orderId  the order ID to cancel
     * @param username the username of the user requesting cancellation
     */
    void cancelOrder(Long orderId, String username);

    /**
     * Filters orders based on multiple parameters.
     *
     * @param userId    optional user ID
     * @param username  optional username
     * @param startDate optional start date for filtering
     * @param endDate   optional end date for filtering
     * @param status    optional order status
     * @return list of filtered order response DTOs
     */
    List<OrderResponse> filterOrders(Long userId, String username, LocalDate startDate, LocalDate endDate, String status);

    /**
     * Filters orders based on string date and status inputs.
     *
     * @param username  optional username
     * @param status    optional status string
     * @param startDate optional start date string (e.g., "2025-06-01")
     * @param endDate   optional end date string (e.g., "2025-06-10")
     * @return list of filtered order response DTOs
     */
    List<OrderResponse> getFilteredOrders(String username, String status, String startDate, String endDate);
}
