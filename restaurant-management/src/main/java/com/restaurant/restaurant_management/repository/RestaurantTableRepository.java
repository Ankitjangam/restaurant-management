package com.restaurant.restaurant_management.repository;

import com.restaurant.restaurant_management.model.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link RestaurantTable} entities.
 * Supports standard CRUD operations for restaurant table management,
 * such as adding, updating, and deleting tables, as well as retrieving table details.
 */
@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {

    // Additional custom methods like findByAvailableTrue() can be added for specific use cases
}
