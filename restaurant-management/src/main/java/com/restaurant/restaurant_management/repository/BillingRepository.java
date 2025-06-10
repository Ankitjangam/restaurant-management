package com.restaurant.restaurant_management.repository;

import com.restaurant.restaurant_management.model.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for managing Billing entities.
 * <p>
 * Extends {@link JpaRepository} to provide basic CRUD operations.
 * <p>
 * Custom Methods:
 * - {@code findByOrderId(Long orderId)}: Fetches billing information for a specific order.
 * - {@code findByUserAndDateRange(String user, LocalDateTime startDate, LocalDateTime endDate)}:
 * Dynamically filters bills based on optional username and date range.
 * Supports partial filtering (any parameter can be null).
 */
@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {

    /**
     * Retrieves the billing record for the specified order.
     *
     * @param orderId the ID of the order
     * @return the Billing entity associated with the order
     */
    Billing findByOrderId(Long orderId);

    /**
     * Retrieves billing records filtered by username and/or date range.
     * Any of the filters (user, startDate, endDate) can be null to ignore that condition.
     *
     * @param user      (optional) the username associated with the order
     * @param startDate (optional) lower bound of order date
     * @param endDate   (optional) upper bound of order date
     * @return list of matching Billing records
     */
    @Query("SELECT b FROM Billing b WHERE (:user IS NULL OR b.order.user.username = :user) AND " +
        "(:startDate IS NULL OR b.order.orderDate >= :startDate) AND (:endDate IS NULL OR b.order.orderDate <= :endDate)")
    List<Billing> findByUserAndDateRange(@Param("user") String user,
                                         @Param("startDate") LocalDateTime startDate,
                                         @Param("endDate") LocalDateTime endDate);
}
