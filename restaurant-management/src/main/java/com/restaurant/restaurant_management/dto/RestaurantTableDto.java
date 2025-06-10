package com.restaurant.restaurant_management.dto;

import lombok.Data;

/**
 * Data Transfer Object representing a restaurant table.
 */
@Data
public class RestaurantTableDto {

    /**
     * Unique identifier of the table.
     */
    private Long id;

    /**
     * Table number or identifier as a string.
     */
    private String tableNumber;

    /**
     * Seating capacity of the table.
     */
    private int capacity;

    /**
     * Availability status of the table.
     */
    private boolean available;
}
