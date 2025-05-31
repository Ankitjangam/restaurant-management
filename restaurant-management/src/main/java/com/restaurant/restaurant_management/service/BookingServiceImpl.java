package com.restaurant.restaurant_management.service;

import com.restaurant.restaurant_management.dto.BookingRequestDTO;
import com.restaurant.restaurant_management.dto.BookingResponseDTO;
import com.restaurant.restaurant_management.exception.ResourceNotFoundException;
import com.restaurant.restaurant_management.model.Booking;
import com.restaurant.restaurant_management.model.BookingStatus;
import com.restaurant.restaurant_management.model.RestaurantTable;
import com.restaurant.restaurant_management.model.User;
import com.restaurant.restaurant_management.repository.BookingRepository;
import com.restaurant.restaurant_management.repository.RestaurantTableRepository;
import com.restaurant.restaurant_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RestaurantTableRepository tableRepository;
    private final UserRepository userRepository;

    @Override
    public BookingResponseDTO createBooking(BookingRequestDTO dto, String username) {
        RestaurantTable table = tableRepository.findById(dto.getTableId())
                .orElseThrow(() -> new ResourceNotFoundException("Table not found with id: " + dto.getTableId()));

        User user = (User) userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + username));

        Booking booking = new Booking();
        booking.setTable(table);  // no casting needed
        booking.setUser(user);
        booking.setStartTime(dto.getStartTime());
        booking.setEndTime(dto.getEndTime());
        booking.setStatus(BookingStatus.CONFIRMED);

        Booking saved = bookingRepository.save(booking);

        return mapToDTO(saved);
    }

    @Override
    public List<BookingResponseDTO> getAllBookings(Long tableId) {
        List<Booking> bookings;
        if (tableId != null) {
            bookings = bookingRepository.findByTableId(tableId);
        } else {
            bookings = bookingRepository.findAll();
        }
        return bookings.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<BookingResponseDTO> getBookingsByUsername(String username) {
        User user = (User) userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + username));
        List<Booking> bookings = bookingRepository.findByUser(user);
        return bookings.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    @Override
    public BookingResponseDTO getBookingById(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
        return mapToDTO(booking);
    }

    private BookingResponseDTO mapToDTO(Booking booking) {
        BookingResponseDTO dto = new BookingResponseDTO();
        dto.setId(booking.getId());
        dto.setUserId(booking.getUser().getId());
        dto.setStartTime(booking.getStartTime());
        dto.setEndTime(booking.getEndTime());
        dto.setStatus(booking.getStatus());
        return dto;
    }
}
