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

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByUser(User user);

    @Query("SELECT o FROM Order o " +
            "WHERE (:user IS NULL OR o.user = :user) " +
            "AND (:status IS NULL OR o.status = :status) " +
            "AND (:startDate IS NULL OR o.orderDate >= :startDate) " +
            "AND (:endDate IS NULL OR o.orderDate <= :endDate)")
    List<Order> filterOrders(
            @Param("user") User user,
            @Param("status") OrderStatus status,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);


    List<Order> findByUserUsernameAndStatusAndOrderDateBetween(String username, OrderStatus status, LocalDateTime start, LocalDateTime end);

    List<Order> findByUserUsernameAndOrderDateBetween(String username, LocalDateTime start, LocalDateTime end);

    List<Order> findByStatusAndOrderDateBetween(OrderStatus status, LocalDateTime start, LocalDateTime end);

    List<Order> findByOrderDateBetween(LocalDateTime start, LocalDateTime end);

}