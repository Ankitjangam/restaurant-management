package com.restaurant.restaurant_management.dto;

import lombok.Data;

import java.util.List;

@Data
public class PlaceOrderRequest {
    private List<OrderItemDTO> items;

    @Data
    public static class OrderItemDTO {
        private Long menuItemId;
        private Integer quantity;
    }
}