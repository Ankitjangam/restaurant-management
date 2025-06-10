package com.restaurant.restaurant_management.service;

import com.restaurant.restaurant_management.dto.BookingRequestDTO;
import com.restaurant.restaurant_management.dto.BookingResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service interface for managing restaurant table bookings.
 * Provides methods to create, retrieve, cancel, and query bookings.
 */
@Service
public interface BookingService {

    /**
     * Creates a new booking for a given user based on the booking request details.
     *
     * @param dto      BookingRequestDTO containing booking details (table, times, etc.)
     * @param username the username of the user creating the booking
     * @return BookingResponseDTO with the created booking information
     */
    BookingResponseDTO createBooking(BookingRequestDTO dto, String username);

    /**
     * Retrieves all bookings for a specific table.
     *
     * @param tableId the ID of the restaurant table
     * @return List of BookingResponseDTO for the specified table
     */
    List<BookingResponseDTO> getAllBookings(Long tableId);

    /**
     * Retrieves all bookings made by a specific user.
     *
     * @param username the username of the user
     * @return List of BookingResponseDTO associated with the user
     */
    List<BookingResponseDTO> getBookingsByUsername(String username);

    /**
     * Cancels a booking identified by its booking ID.
     *
     * @param bookingId the ID of the booking to cancel
     */
    void cancelBooking(Long bookingId);

    /**
     * Retrieves booking details by booking ID.
     *
     * @param bookingId the ID of the booking
     * @return BookingResponseDTO containing booking details
     */
    BookingResponseDTO getBookingById(Long bookingId);
}
