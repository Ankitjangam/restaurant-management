package com.restaurant.restaurant_management.service;


import com.restaurant.restaurant_management.dto.OrderResponse;
import com.restaurant.restaurant_management.dto.PlaceOrderRequest;
import com.restaurant.restaurant_management.model.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    Order placeOrder(PlaceOrderRequest request);
    OrderResponse getOrderById(Long orderId);
    List<OrderResponse> getAllOrders();
    List<OrderResponse> getOrdersByStatus(String status);
    List<OrderResponse> getOrdersForCustomer(String username);
    void updateOrderStatus(Long orderId, String status);
    void cancelOrder(Long orderId, String username);
    List<OrderResponse> filterOrders(Long userId, String username, LocalDate startDate, LocalDate endDate, String status);
    List<OrderResponse> getFilteredOrders(String username, String status, String startDate, String endDate);



}