package com.restaurant.restaurant_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing an individual item within an order.
 * Stores quantity, price at order time, and links to the order and menu item.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem {

    /**
     * Unique identifier for the order item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Quantity of the menu item ordered.
     * Cannot be null.
     */
    @Column(nullable = false)
    private Integer quantity;

    /**
     * Price per item at the time the order was placed.
     * Cannot be null.
     * Stored separately to maintain historical price data.
     */
    @Column(nullable = false)
    private Double price;

    /**
     * Reference to the parent order.
     * Many order items belong to one order.
     * Lazy loading used for performance.
     * Cannot be null.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    /**
     * Reference to the menu item ordered.
     * Many order items can refer to one menu item.
     * Lazy loading used for performance.
     * Cannot be null.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;
}
