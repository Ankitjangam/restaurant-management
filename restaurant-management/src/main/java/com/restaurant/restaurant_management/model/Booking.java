package com.restaurant.restaurant_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represents a table booking made by a user for a specific time slot.
 * Each booking is linked to a user and a restaurant table, with a status indicating the booking state.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {

    /**
     * Unique identifier for the booking record (Primary Key).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user who made the booking.
     * Many bookings can belong to one user.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The restaurant table associated with this booking.
     * Many bookings can refer to the same table (at different time slots).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id")
    private RestaurantTable table;

    /**
     * Start time of the booking (date and time when the booking begins).
     */
    private LocalDateTime startTime;

    /**
     * End time of the booking (date and time when the booking ends).
     */
    private LocalDateTime endTime;

    /**
     * Status of the booking (e.g., PENDING, CONFIRMED, CANCELLED).
     */
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}
