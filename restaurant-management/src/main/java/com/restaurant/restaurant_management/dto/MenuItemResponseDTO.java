package com.restaurant.restaurant_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for sending Menu Item details in responses.
 * Contains basic item info along with its category details.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemResponseDTO {
    /**
     * Unique identifier of the menu item.
     */
    private Long id;

    /**
     * Name of the menu item.
     */
    private String name;

    /**
     * Description of the menu item.
     */
    private String description;

    /**
     * Price of the menu item.
     */
    private Double price;

    /**
     * Category ID to which this menu item belongs.
     */
    private Long categoryId;

    /**
     * Name of the category.
     */
    private String categoryName;
}
