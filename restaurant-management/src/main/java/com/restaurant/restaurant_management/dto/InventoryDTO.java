package com.restaurant.restaurant_management.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryDTO {
    private Long id;
    private String itemName;
    private Double quantity;
    private String unit;
    private Double pricePerUnit;
}
