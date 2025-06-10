package com.restaurant.restaurant_management.service;

import com.restaurant.restaurant_management.dto.RestaurantRequestDTO;
import com.restaurant.restaurant_management.dto.RestaurantResponseDTO;

import java.util.List;

public interface RestaurantService {
    RestaurantResponseDTO createRestaurant(RestaurantRequestDTO dto);

    List<RestaurantResponseDTO> getAllRestaurants();
}
