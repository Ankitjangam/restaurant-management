package com.restaurant.restaurant_management.repository;

import com.restaurant.restaurant_management.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link Category} entity.
 * <p>
 * Provides CRUD operations for managing menu item categories,
 * such as starters, main course, desserts, etc.
 * <p>
 * Extends {@link JpaRepository} to leverage Spring Data JPA capabilities.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
