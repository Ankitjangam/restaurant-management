package com.restaurant.restaurant_management.dto;

import lombok.Data;

/**
 * DTO for creating or updating a restaurant table.
 */
@Data
public class TableRequestDTO {

    /**
     * Table number or identifier as a string.
     */
    private String tableNumber;

    /**
     * Seating capacity of the table.
     */
    private int capacity;
}
