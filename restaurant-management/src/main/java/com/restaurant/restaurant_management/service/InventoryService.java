package com.restaurant.restaurant_management.service;

import com.restaurant.restaurant_management.dto.InventoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service interface for managing inventory items.
 */
@Service
public interface InventoryService {

    /**
     * Creates a new inventory record.
     *
     * @param dto the inventory data transfer object
     * @return the created inventory DTO with generated ID
     */
    InventoryDTO createInventory(InventoryDTO dto);

    /**
     * Updates an existing inventory record by ID.
     *
     * @param id  the ID of the inventory to update
     * @param dto the updated inventory DTO
     * @return the updated inventory DTO
     */
    InventoryDTO updateInventory(Long id, InventoryDTO dto);

    /**
     * Retrieves inventory by its ID.
     *
     * @param id the ID of the inventory
     * @return the found inventory DTO
     */
    InventoryDTO getInventoryById(Long id);

    /**
     * Retrieves all inventory items.
     *
     * @return list of all inventory DTOs
     */
    List<InventoryDTO> getAllInventory();

    /**
     * Deletes an inventory record by ID.
     *
     * @param id the ID of the inventory to delete
     */
    void deleteInventory(Long id);
}
