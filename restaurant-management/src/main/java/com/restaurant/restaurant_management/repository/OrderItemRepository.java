package com.restaurant.restaurant_management.repository;

import com.restaurant.restaurant_management.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link OrderItem} entities.
 * <p>
 * Extends {@link JpaRepository} to provide CRUD operations for order item records.
 * This interface allows the application to persist and retrieve item-level details
 * related to specific customer orders.
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // You can add custom query methods here if needed in the future
}
