package com.restaurant.restaurant_management.service;

import com.restaurant.restaurant_management.dto.BookingRequestDTO;
import com.restaurant.restaurant_management.dto.BookingResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingService {

    BookingResponseDTO createBooking(BookingRequestDTO dto, String username);

    List<BookingResponseDTO> getAllBookings(Long tableId);

    List<BookingResponseDTO> getBookingsByUsername(String username);

    void cancelBooking(Long bookingId);

    BookingResponseDTO getBookingById(Long bookingId);
}
