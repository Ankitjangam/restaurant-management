package com.restaurant.restaurant_management.repository;

import com.restaurant.restaurant_management.model.Order;
import com.restaurant.restaurant_management.model.OrderStatus;
import com.restaurant.restaurant_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for {@link Order} entity operations.
 * Provides methods to perform CRUD and custom queries on customer orders.
 * Useful for analytics, filtering, and order management by users and admins.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Find all orders by a specific status.
     *
     * @param status the order status (e.g., CONFIRMED, CANCELLED)
     * @return list of matching orders
     */
    List<Order> findByStatus(OrderStatus status);

    /**
     * Find all orders placed by a specific user.
     *
     * @param user the user object
     * @return list of user's orders
     */
    List<Order> findByUser(User user);

    /**
     * Filter orders based on optional user, status, and date range.
     * If a parameter is null, that filter is skipped.
     *
     * @param user      optional user to filter by
     * @param status    optional order status to filter by
     * @param startDate optional start of date range
     * @param endDate   optional end of date range
     * @return list of filtered orders
     */
    @Query("SELECT o FROM Order o " +
        "WHERE (:user IS NULL OR o.user = :user) " +
        "AND (:status IS NULL OR o.status = :status) " +
        "AND (:startDate IS NULL OR o.orderDate >= :startDate) " +
        "AND (:endDate IS NULL OR o.orderDate <= :endDate)")
    List<Order> filterOrders(
        @Param("user") User user,
        @Param("status") OrderStatus status,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    /**
     * Find orders by username, status, and within a specific date range.
     */
    List<Order> findByUserUsernameAndStatusAndOrderDateBetween(
        String username,
        OrderStatus status,
        LocalDateTime start,
        LocalDateTime end
    );

    /**
     * Find orders by username within a specific date range.
     */
    List<Order> findByUserUsernameAndOrderDateBetween(
        String username,
        LocalDateTime start,
        LocalDateTime end
    );

    /**
     * Find orders by status within a date range.
     */
    List<Order> findByStatusAndOrderDateBetween(
        OrderStatus status,
        LocalDateTime start,
        LocalDateTime end
    );

    /**
     * Find all orders placed between two given timestamps.
     */
    List<Order> findByOrderDateBetween(
        LocalDateTime start,
        LocalDateTime end
    );
}
