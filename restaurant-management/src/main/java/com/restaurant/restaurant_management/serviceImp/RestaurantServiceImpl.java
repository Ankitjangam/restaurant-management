package com.restaurant.restaurant_management.serviceImp;

import com.restaurant.restaurant_management.dto.RestaurantRequestDTO;
import com.restaurant.restaurant_management.dto.RestaurantResponseDTO;
import com.restaurant.restaurant_management.model.Restaurant;
import com.restaurant.restaurant_management.repository.RestaurantRepository;
import com.restaurant.restaurant_management.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Override
    public RestaurantResponseDTO createRestaurant(RestaurantRequestDTO dto) {
        Restaurant restaurant = Restaurant.builder()
                .name(dto.getName())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .build();

        Restaurant saved = restaurantRepository.save(restaurant);

        return new RestaurantResponseDTO(saved.getId(), saved.getName(), saved.getAddress(), saved.getPhone());
    }

    @Override
    public List<RestaurantResponseDTO> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(r -> new RestaurantResponseDTO(r.getId(), r.getName(), r.getAddress(), r.getPhone()))
                .toList();
    }
}
