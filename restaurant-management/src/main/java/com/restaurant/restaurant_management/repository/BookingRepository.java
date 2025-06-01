package com.restaurant.restaurant_management.repository;

import com.restaurant.restaurant_management.model.Booking;
import com.restaurant.restaurant_management.model.BookingStatus;
import com.restaurant.restaurant_management.model.RestaurantTable;
import com.restaurant.restaurant_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Find bookings for a table that overlap with a given time range (for conflict check)
    List<Booking> findByTableAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
            RestaurantTable table, LocalDateTime endTime, LocalDateTime startTime);

    // Find all bookings for a user
    List<Booking> findByUser(User user);

    List<Booking> findByTableId(Long tableId);
    List<Booking> findByStatus(BookingStatus status);
    List<Booking> findByUserId(Long userId);

}
