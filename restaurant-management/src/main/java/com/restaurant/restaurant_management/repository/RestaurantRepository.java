package com.restaurant.restaurant_management.repository;

import com.restaurant.restaurant_management.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository  extends JpaRepository<Restaurant, Long> {
}
