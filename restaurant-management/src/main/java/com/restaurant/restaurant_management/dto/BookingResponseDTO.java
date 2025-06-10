package com.restaurant.restaurant_management.dto;

import com.restaurant.restaurant_management.model.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for booking response details.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO {

    /**
     * Unique identifier for the booking.
     */
    private Long id;

    /**
     * ID of the user who made the booking.
     */
    private Long userId;

    /**
     * ID of the booked table.
     */
    private Long tableId;

    /**
     * Start time of the booking.
     */
    private LocalDateTime startTime;

    /**
     * End time of the booking.
     */
    private LocalDateTime endTime;

    /**
     * Status of the booking (e.g., CONFIRMED, CANCELLED).
     */
    private BookingStatus status;
}
