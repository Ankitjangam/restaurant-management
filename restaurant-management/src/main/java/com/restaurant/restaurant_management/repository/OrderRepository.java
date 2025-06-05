package com.restaurant.restaurant_management.repository;

import com.restaurant.restaurant_management.model.Order;
import com.restaurant.restaurant_management.model.OrderStatus;
import com.restaurant.restaurant_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByUser(User user);
}