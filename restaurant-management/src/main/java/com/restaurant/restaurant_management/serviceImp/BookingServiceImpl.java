package com.restaurant.restaurant_management.serviceImp;

import com.restaurant.restaurant_management.dto.BookingRequestDTO;
import com.restaurant.restaurant_management.dto.BookingResponseDTO;
import com.restaurant.restaurant_management.enums.BookingStatus;
import com.restaurant.restaurant_management.exception.ResourceNotFoundException;
import com.restaurant.restaurant_management.model.Booking;
import com.restaurant.restaurant_management.model.RestaurantTable;
import com.restaurant.restaurant_management.model.Role;
import com.restaurant.restaurant_management.model.User;
import com.restaurant.restaurant_management.repository.BookingRepository;
import com.restaurant.restaurant_management.repository.RestaurantTableRepository;
import com.restaurant.restaurant_management.repository.UserRepository;
import com.restaurant.restaurant_management.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service implementation for managing bookings.
 */
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RestaurantTableRepository tableRepository;
    private final UserRepository userRepository;

    /**
     * Creates a booking for a user at a specified restaurant table.
     *
     * @param dto      the booking request details including tableId, startTime, endTime
     * @param username the email/username of the user making the booking
     * @return BookingResponseDTO containing the booking details
     * @throws ResourceNotFoundException if table or user not found
     */
    @Override
    public BookingResponseDTO createBooking(BookingRequestDTO dto, String username) {
        // Fetch restaurant table by id, or throw if not found
        RestaurantTable table = tableRepository.findById(dto.getTableId())
                .orElseThrow(() -> new ResourceNotFoundException("Table not found with id: " + dto.getTableId()));

        // Fetch user by email (username), or throw if not found
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + username));

        // Create and populate booking entity
        Booking booking = new Booking();
        booking.setTable(table);
        booking.setUser(user);
        booking.setStartTime(dto.getStartTime());
        booking.setEndTime(dto.getEndTime());
        booking.setStatus(BookingStatus.CONFIRMED);

        // Save booking to repository
        Booking saved = bookingRepository.save(booking);

        // Map saved entity to response DTO and return
        return mapToDTO(saved);
    }

    /**
     * Retrieves all bookings optionally filtered by a specific table ID.
     *
     * @param tableId the restaurant table ID to filter bookings (optional)
     * @return list of BookingResponseDTO matching the filter criteria
     */
    @Override
    public List<BookingResponseDTO> getAllBookings(Long tableId) {
        List<Booking> bookings;

        if (tableId != null) {
            bookings = bookingRepository.findByTableId(tableId);
        } else {
            bookings = bookingRepository.findAll();
        }

        return bookings.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all bookings made by a specific user identified by username/email.
     *
     * @param username the email/username of the user
     * @return list of BookingResponseDTO for the user
     * @throws ResourceNotFoundException if user not found
     */
    @Override
    public List<BookingResponseDTO> getBookingsByUsername(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + username));

        List<Booking> bookings = bookingRepository.findByUser(user);

        return bookings.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Cancels a booking by updating its status to CANCELLED.
     *
     * @param bookingId the ID of the booking to cancel
     * @throws ResourceNotFoundException if booking not found
     */
    @Override
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    /**
     * Retrieves a booking by its ID.
     *
     * @param bookingId the ID of the booking
     * @return BookingResponseDTO representing the booking details
     * @throws ResourceNotFoundException if booking not found
     */
    @Override
    public BookingResponseDTO getBookingById(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));

        return mapToDTO(booking);
    }

    /**
     * Maps a Booking entity to a BookingResponseDTO.
     *
     * @param booking the Booking entity
     * @return the mapped BookingResponseDTO
     */
    private BookingResponseDTO mapToDTO(Booking booking) {
        return BookingResponseDTO.builder()
                .id(booking.getId())
                .userId(booking.getUser().getId())
                .tableId(booking.getTable().getId())
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .status(booking.getStatus())
                .build();
    }

    /**
     * Custom implementation of Spring Security's UserDetails
     * to integrate the application's User entity with Spring Security.
     */
    public static class CustomUserDetails implements UserDetails {

        private final User user;

        /**
         * Constructs a CustomUserDetails object wrapping the User entity.
         *
         * @param user the User entity to wrap
         */
        public CustomUserDetails(User user) {
            this.user = user;
        }

        /**
         * Returns the email of the user.
         *
         * @return user's email address
         */
        public String getEmail() {
            return user.getEmail();
        }

        /**
         * Returns the roles assigned to the user.
         *
         * @return set of roles
         */
        public Set<Role> getRoles() {
            return user.getRoles();
        }

        /**
         * Returns the authorities granted to the user.
         * Each Role is mapped to a GrantedAuthority with prefix "ROLE_".
         *
         * @return collection of granted authorities
         */
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return user.getRoles().stream()
                    .map(role -> (GrantedAuthority) () -> "ROLE_" + role.getName())
                    .collect(Collectors.toSet());
        }

        /**
         * Returns the password used to authenticate the user.
         *
         * @return user's password
         */
        @Override
        public String getPassword() {
            return user.getPassword();
        }

        /**
         * Returns the username used to authenticate the user.
         * Here we use email as username.
         *
         * @return user's email as username
         */
        @Override
        public String getUsername() {
            return user.getEmail();
        }

        /**
         * Indicates whether the user's account has expired.
         * Always returns true for this implementation (non-expiring account).
         *
         * @return true if account is non-expired
         */
        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        /**
         * Indicates whether the user is locked or unlocked.
         * Always returns true for this implementation (non-locked account).
         *
         * @return true if account is non-locked
         */
        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        /**
         * Indicates whether the user's credentials (password) has expired.
         * Always returns true for this implementation (credentials non-expired).
         *
         * @return true if credentials are non-expired
         */
        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        /**
         * Indicates whether the user is enabled or disabled.
         * Always returns true for this implementation (user enabled).
         *
         * @return true if user is enabled
         */
        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
