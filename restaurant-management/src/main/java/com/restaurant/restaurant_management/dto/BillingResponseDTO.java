package com.restaurant.restaurant_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Billing response details.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingResponseDTO {

    /**
     * Unique identifier of the billing record.
     */
    private Long id;

    /**
     * Associated Order ID for which billing is done.
     */
    private Long orderId;

    /**
     * Price before tax and discount.
     */
    private Double price;

    /**
     * Tax amount applied on the price.
     */
    private Double tax;

    /**
     * Discount amount applied on the price.
     */
    private Double discount;

    /**
     * Final total amount after tax and discount.
     */
    private Double totalAmount;
}
