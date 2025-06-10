package com.restaurant.restaurant_management.controller;

import com.restaurant.restaurant_management.dto.BookingRequestDTO;
import com.restaurant.restaurant_management.dto.BookingResponseDTO;
import com.restaurant.restaurant_management.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * REST controller for managing restaurant table bookings.
 */
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    /**
     * Create a new booking for the authenticated user.
     *
     * @param dto       booking request data
     * @param principal currently authenticated user
     * @return created BookingResponseDTO
     */
    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(@RequestBody BookingRequestDTO dto, Principal principal) {
        BookingResponseDTO booking = bookingService.createBooking(dto, principal.getName());
        return ResponseEntity.ok(booking);
    }

    /**
     * Retrieve all bookings, optionally filtered by tableId.
     *
     * @param tableId optional table ID filter
     * @return list of BookingResponseDTO
     */
    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> getAllBookings(
            @RequestParam(required = false) Long tableId
    ) {
        List<BookingResponseDTO> bookings = bookingService.getAllBookings(tableId);
        return ResponseEntity.ok(bookings);
    }

    /**
     * Retrieve all bookings for the currently authenticated user.
     *
     * @param principal currently authenticated user
     * @return list of BookingResponseDTO
     */
    @GetMapping("/my")
    public ResponseEntity<List<BookingResponseDTO>> getMyBookings(Principal principal) {
        List<BookingResponseDTO> bookings = bookingService.getBookingsByUsername(principal.getName());
        return ResponseEntity.ok(bookings);
    }

    /**
     * Cancel a booking by its ID.
     *
     * @param bookingId booking ID to cancel
     * @return success message
     */
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok("Booking cancelled successfully.");
    }

    /**
     * Get booking details by booking ID.
     *
     * @param bookingId booking ID
     * @return BookingResponseDTO
     */
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponseDTO> getBookingById(@PathVariable Long bookingId) {
        BookingResponseDTO dto = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(dto);
    }
}
