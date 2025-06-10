package com.restaurant.restaurant_management.controller;

import com.restaurant.restaurant_management.dto.RestaurantRequestDTO;
import com.restaurant.restaurant_management.dto.RestaurantResponseDTO;
import com.restaurant.restaurant_management.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<RestaurantResponseDTO> createRestaurant(@RequestBody RestaurantRequestDTO dto) {
        return new ResponseEntity<>(restaurantService.createRestaurant(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponseDTO>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }
}
