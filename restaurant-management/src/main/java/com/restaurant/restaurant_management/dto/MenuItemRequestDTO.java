package com.restaurant.restaurant_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Menu Item creation or update requests.
 * Includes validation annotations to ensure data integrity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemRequestDTO {

    /**
     * Name of the menu item.
     * Must not be blank.
     */
    @NotBlank(message = "Name is mandatory")
    private String name;

    /**
     * Description of the menu item.
     * Must not be blank.
     */
    @NotBlank(message = "Description is mandatory")
    private String description;

    /**
     * Price of the menu item.
     * Must not be null and must be a positive number.
     */
    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be positive")
    private Double price;

    /**
     * ID of the category to which this menu item belongs.
     * Must not be null.
     */
    @NotNull(message = "Category ID is mandatory")
    private Long categoryId;

    @NotNull(message = "Restaurant ID is mandatory")
    private Long restaurantId;

}
