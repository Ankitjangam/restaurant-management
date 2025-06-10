package com.restaurant.restaurant_management.repository;

import com.restaurant.restaurant_management.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Inventory} entities.
 * <p>
 * Provides standard CRUD operations and custom finder methods
 * to manage inventory items used by the restaurant, such as ingredients or stock.
 * <p>
 * Extends {@link JpaRepository} to leverage Spring Data JPA capabilities.
 */
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    /**
     * Finds an inventory item by its name.
     *
     * @param name the name of the inventory item
     * @return an Optional containing the Inventory item if found
     */
    Optional<Inventory> findByItemName(String name);
}
