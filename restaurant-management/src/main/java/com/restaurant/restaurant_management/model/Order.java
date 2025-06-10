package com.restaurant.restaurant_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity representing a customer order in the restaurant management system.
 * Contains order details such as date, status, total amount, and associated user.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    /**
     * Unique identifier for the order.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Timestamp when the order was placed.
     * Cannot be null.
     */
    @Column(nullable = false)
    private LocalDateTime orderDate;

    /**
     * Current status of the order.
     * Stored as a String value in the database.
     * Cannot be null and limited to 20 characters.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status;

    /**
     * Total amount for the order.
     * Cannot be null.
     */
    @Column(nullable = false)
    private Double totalAmount;

    /**
     * List of order items associated with this order.
     * Cascade operations ensure order items are saved/removed along with the order.
     * Lazy fetching is used for performance optimization.
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    /**
     * User who placed the order.
     * Many orders can be placed by one user.
     * Lazy fetching for performance, and user_id cannot be null.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
