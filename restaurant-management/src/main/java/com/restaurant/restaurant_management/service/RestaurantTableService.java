package com.restaurant.restaurant_management.service;

import com.restaurant.restaurant_management.dto.RestaurantTableDto;
import com.restaurant.restaurant_management.dto.TableRequestDTO;
import com.restaurant.restaurant_management.model.RestaurantTable;
import org.springframework.stereotype.Service;

/**
 * Service interface for managing restaurant tables.
 */
@Service
public interface RestaurantTableService {

    /**
     * Creates a new restaurant table.
     *
     * @param dto the table request DTO containing creation details
     * @return the created RestaurantTable entity
     */
    RestaurantTable createTable(TableRequestDTO dto);

    /**
     * Retrieves a restaurant table by ID.
     *
     * @param id the table ID
     * @return the table DTO
     */
    RestaurantTableDto getTable(Long id);

    /**
     * Updates a restaurant table.
     *
     * @param id  the ID of the table to update
     * @param dto the DTO containing updated table details
     * @return the updated table DTO
     */
    RestaurantTableDto updateTable(Long id, RestaurantTableDto dto);

    /**
     * Deletes a restaurant table by ID.
     *
     * @param id the ID of the table to delete
     */
    void deleteTable(Long id);
}
