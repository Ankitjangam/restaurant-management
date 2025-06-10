package com.restaurant.restaurant_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO representing the response details of an Order.
 */
@Data
@AllArgsConstructor
public class OrderResponse {

    /**
     * Unique identifier for the order.
     */
    private Long id;

    /**
     * Date and time when the order was placed.
     */
    private LocalDateTime orderDate;

    /**
     * Current status of the order (e.g., PENDING, COMPLETED).
     */
    private String status;

    /**
     * Total amount for the order.
     */
    private Double totalAmount;
}
