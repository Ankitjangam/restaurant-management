package com.restaurant.restaurant_management.controller;

import com.restaurant.restaurant_management.dto.BookingRequestDTO;
import com.restaurant.restaurant_management.dto.BookingResponseDTO;
import com.restaurant.restaurant_management.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {


    private final BookingService bookingService;

    // ✅ Create a new booking (CUSTOMER)
    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(@RequestBody BookingRequestDTO dto, Principal principal) {
        BookingResponseDTO booking = bookingService.createBooking(dto, principal.getName());
        return ResponseEntity.ok(booking);
    }

    // ✅ Get all bookings (ADMIN, STAFF)
    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> getAllBookings(
            @RequestParam(required = false) Long tableId
    ) {
        List<BookingResponseDTO> bookings = bookingService.getAllBookings(tableId);
        return ResponseEntity.ok(bookings);
    }

    // ✅ Get bookings for current logged-in customer
    @GetMapping("/my")
    public ResponseEntity<List<BookingResponseDTO>> getMyBookings(Principal principal) {
        List<BookingResponseDTO> bookings = bookingService.getBookingsByUsername(principal.getName());
        return ResponseEntity.ok(bookings);
    }

    // ✅ Admin can cancel a booking
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok("Booking cancelled successfully.");
    }

    // ✅ (Optional) Get booking by ID
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponseDTO> getBookingById(@PathVariable Long bookingId) {
        BookingResponseDTO dto = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(dto);
    }
}
