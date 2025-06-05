package com.restaurant.restaurant_management.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private LocalDateTime orderDate;
    private String status;
    private Double totalAmount;
}