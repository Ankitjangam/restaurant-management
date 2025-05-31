package com.restaurant.restaurant_management.service;

import com.restaurant.restaurant_management.dto.RestaurantTableDto;
import com.restaurant.restaurant_management.dto.TableRequestDTO;
import com.restaurant.restaurant_management.model.RestaurantTable;
import org.springframework.stereotype.Service;

@Service
public interface RestaurantTableService {
    RestaurantTable createTable(TableRequestDTO dto);
    RestaurantTableDto getTable(Long id);
    RestaurantTableDto updateTable(Long id, RestaurantTableDto dto);
    void deleteTable(Long id);
}
