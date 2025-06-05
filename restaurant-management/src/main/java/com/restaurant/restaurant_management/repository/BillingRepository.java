package com.restaurant.restaurant_management.repository;

import com.restaurant.restaurant_management.model.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {

    Billing findByOrderId(Long orderId);

    @Query("SELECT b FROM Billing b WHERE (:user IS NULL OR b.order.user.username = :user) AND " +
            "(:startDate IS NULL OR b.order.orderDate >= :startDate) AND (:endDate IS NULL OR b.order.orderDate <= :endDate)")
    List<Billing> findByUserAndDateRange(@Param("user") String user,
                                         @Param("startDate") LocalDateTime startDate,
                                         @Param("endDate") LocalDateTime endDate);
}
