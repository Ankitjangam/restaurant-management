package com.restaurant.restaurant_management.repository;

import com.restaurant.restaurant_management.model.Booking;
import com.restaurant.restaurant_management.model.BookingStatus;
import com.restaurant.restaurant_management.model.RestaurantTable;
import com.restaurant.restaurant_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for managing {@link Booking} entities.
 * <p>
 * Extends {@link JpaRepository} to provide standard CRUD functionality.
 * <p>
 * Includes custom queries for time conflict detection, user-specific bookings,
 * and filtering by table or status.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    /**
     * Finds bookings that overlap with the given time range for a specific table.
     * Used for checking table availability and booking conflicts.
     *
     * @param table     the restaurant table
     * @param endTime   proposed end time of new booking
     * @param startTime proposed start time of new booking
     * @return list of conflicting bookings
     */
    List<Booking> findByTableAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
        RestaurantTable table, LocalDateTime endTime, LocalDateTime startTime
    );

    /**
     * Finds all bookings placed by a specific user.
     *
     * @param user the user
     * @return list of bookings
     */
    List<Booking> findByUser(User user);

    /**
     * Retrieves all bookings associated with a specific table by its ID.
     *
     * @param tableId the table ID
     * @return list of bookings
     */
    List<Booking> findByTableId(Long tableId);

    /**
     * Retrieves all bookings with a specific booking status.
     *
     * @param status the booking status (e.g., CONFIRMED, CANCELLED)
     * @return list of bookings
     */
    List<Booking> findByStatus(BookingStatus status);

    /**
     * Retrieves all bookings made by a user using their user ID.
     *
     * @param userId the user's ID
     * @return list of bookings
     */
    List<Booking> findByUserId(Long userId);
}
