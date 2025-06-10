package com.restaurant.restaurant_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the billing information associated with a specific order.
 * Each billing entry is uniquely linked to an order and contains detailed pricing breakdown.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "billing")
public class Billing {

    /**
     * Unique identifier for the billing record (Primary Key).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * One-to-one relationship with the Order entity.
     * Each billing record corresponds to exactly one order.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    /**
     * Base price of the order before applying tax and discount.
     */
    @Column(nullable = false)
    private Double price;

    /**
     * Tax amount applicable on the base price.
     */
    @Column(nullable = false)
    private Double tax;

    /**
     * Discount amount applicable on the order.
     */
    @Column(nullable = false)
    private Double discount;

    /**
     * Final payable amount calculated as (price + tax - discount).
     */
    @Column(nullable = false)
    private Double totalAmount;
}
