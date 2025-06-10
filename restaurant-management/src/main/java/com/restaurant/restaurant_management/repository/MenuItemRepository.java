package com.restaurant.restaurant_management.repository;

import com.restaurant.restaurant_management.model.Category;
import com.restaurant.restaurant_management.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link MenuItem} entities.
 * <p>
 * Provides methods for accessing menu items, including filtering by category.
 * This interface extends {@link JpaRepository}, giving access to basic CRUD operations
 * and the ability to define custom queries for more specific use cases.
 */
@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    /**
     * Finds all menu items associated with the given category ID.
     *
     * @param categoryId the ID of the category
     * @return a list of menu items in the specified category
     */
    List<MenuItem> findByCategoryId(Long categoryId);

    /**
     * Finds all menu items belonging to the given {@link Category} entity.
     *
     * @param category the category entity
     * @return a list of menu items in the specified category
     */
    List<MenuItem> findByCategory(Category category);
}
