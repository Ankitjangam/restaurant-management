package com.restaurant.restaurant_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingResponseDTO {

    private Long Id;
    private Long orderId;
    private Double price;
    private Double tax;
    private Double discount;
    private Double totalAmount;
}
