package com.restaurant.restaurant_management.dto;

import lombok.Data;

@Data
public class RestaurantTableDto {
    private Long id;
    private String tableNumber;
    private int capacity;
    private boolean available;
}
