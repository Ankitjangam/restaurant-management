package com.restaurant.restaurant_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity representing an inventory item in the restaurant.
 * Tracks the quantity, unit, price per unit, and last update timestamp.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {

    /**
     * Unique identifier for each inventory item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the inventory item (e.g., Tomato, Cheese, Olive Oil).
     */
    private String itemName;

    /**
     * Quantity of the inventory item currently in stock.
     */
    private Double quantity;

    /**
     * Unit of measurement for the quantity (e.g., kg, liters, pieces).
     */
    private String unit;

    /**
     * Price per unit of the inventory item.
     */
    private Double pricePerUnit;

    /**
     * Timestamp indicating the last time this inventory item was updated.
     * Automatically set before insert or update operations.
     */
    private LocalDateTime lastUpdated;

    /**
     * Lifecycle callback to update the `lastUpdated` timestamp before
     * persisting or updating the entity in the database.
     */
    @PrePersist
    @PreUpdate
    public void setTimestamps() {
        this.lastUpdated = LocalDateTime.now();
    }
}
