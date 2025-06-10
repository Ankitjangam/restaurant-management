package com.restaurant.restaurant_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for booking request details.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequestDTO {

    /**
     * ID of the user making the booking.
     */
    private Long userId;

    /**
     * ID of the restaurant table to be booked.
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
}
