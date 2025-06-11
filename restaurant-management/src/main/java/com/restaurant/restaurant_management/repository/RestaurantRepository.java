package com.restaurant.restaurant_management.repository;

import com.restaurant.restaurant_management.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing CRUD operations on {@link Restaurant} entities.
 * Enables management of restaurant information like name, address, and menu associations.
 */
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}
