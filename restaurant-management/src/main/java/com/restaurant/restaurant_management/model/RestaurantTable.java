package com.restaurant.restaurant_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a Table in a Restaurant.
 * Stores details like table number, seating capacity, and availability status.
 * <p>
 * This entity is used to manage restaurant seating arrangements and availability.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantTable {

    /**
     * Primary key for RestaurantTable.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique identifier or number of the table within the restaurant.
     */
    private String tableNumber;

    /**
     * Seating capacity of the table.
     */
    private int capacity;

    /**
     * Availability status of the table.
     * Defaults to true, indicating the table is available for booking.
     */
    @Column(name = "available")
    @Builder.Default
    private boolean available = true;
}
