package com.restaurant.restaurant_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Inventory details.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryDTO {

    /**
     * Unique identifier for the inventory item.
     */
    private Long id;

    /**
     * Name of the inventory item.
     */
    private String itemName;

    /**
     * Quantity available in inventory.
     */
    private Double quantity;

    /**
     * Unit of measurement for the quantity (e.g., kg, liters).
     */
    private String unit;

    /**
     * Price per unit of the inventory item.
     */
    private Double pricePerUnit;
}
