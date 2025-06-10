package com.restaurant.restaurant_management.dto;

import lombok.Data;

import java.util.List;

/**
 * DTO representing a request to place an order,
 * containing a list of order items.
 */
@Data
public class PlaceOrderRequest {

    /**
     * List of order items included in the order.
     */
    private List<OrderItemDTO> items;

    /**
     * DTO representing an individual item within an order.
     */
    @Data
    public static class OrderItemDTO {

        /**
         * ID of the menu item being ordered.
         */
        private Long menuItemId;

        /**
         * Quantity of the menu item ordered.
         */
        private Integer quantity;
    }
}
