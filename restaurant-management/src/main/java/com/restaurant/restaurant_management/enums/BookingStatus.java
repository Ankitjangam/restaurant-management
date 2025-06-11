package com.restaurant.restaurant_management.enums;

/**
 * Enum representing the different statuses a booking or order can have
 * in the restaurant management system.
 */
public enum BookingStatus {

    /**
     * Booking or order has been placed by the user but not yet confirmed.
     */
    PLACED,

    /**
     * Booking or order has been confirmed by the restaurant.
     */
    CONFIRMED,

    /**
     * The order is currently being prepared.
     */
    PREPARING,

    /**
     * The order is ready for pickup or delivery.
     */
    READY,

    /**
     * The order has been delivered to the customer.
     */
    DELIVERED,

    /**
     * The booking or order has been cancelled.
     */
    CANCELLED
}
