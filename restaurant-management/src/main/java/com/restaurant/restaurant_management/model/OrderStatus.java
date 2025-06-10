package com.restaurant.restaurant_management.model;

/**
 * Enum representing the various statuses an Order can have in the system.
 * Used to track and manage the lifecycle of an order.
 */
public enum OrderStatus {
    /**
     * Order has been created but not yet confirmed.
     */
    PENDING,

    /**
     * Order has been confirmed and will be processed.
     */
    CONFIRMED,

    /**
     * Order is currently being prepared.
     */
    PREPARING,

    /**
     * Order has been completed and delivered or picked up.
     */
    COMPLETED,

    /**
     * Order has been cancelled and will not be processed.
     */
    CANCELLED
}
