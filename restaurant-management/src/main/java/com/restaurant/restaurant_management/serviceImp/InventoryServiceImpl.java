package com.restaurant.restaurant_management.serviceImp;

import com.restaurant.restaurant_management.dto.InventoryDTO;
import com.restaurant.restaurant_management.model.Inventory;
import com.restaurant.restaurant_management.repository.InventoryRepository;
import com.restaurant.restaurant_management.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing inventory items.
 */
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    /**
     * Converts Inventory entity to InventoryDTO.
     *
     * @param inventory Inventory entity
     * @return InventoryDTO representation
     */
    private InventoryDTO convertToDTO(Inventory inventory) {
        return InventoryDTO.builder()
            .id(inventory.getId())
            .itemName(inventory.getItemName())
            .quantity(inventory.getQuantity())
            .unit(inventory.getUnit())
            .pricePerUnit(inventory.getPricePerUnit())
            .build();
    }

    /**
     * Converts InventoryDTO to Inventory entity.
     *
     * @param dto InventoryDTO
     * @return Inventory entity
     */
    private Inventory convertToEntity(InventoryDTO dto) {
        return Inventory.builder()
            .id(dto.getId())
            .itemName(dto.getItemName())
            .quantity(dto.getQuantity())
            .unit(dto.getUnit())
            .pricePerUnit(dto.getPricePerUnit())
            .build();
    }

    /**
     * Creates a new inventory item.
     *
     * @param dto Inventory data transfer object with item details
     * @return created InventoryDTO
     */
    @Override
    public InventoryDTO createInventory(InventoryDTO dto) {
        Inventory inventory = inventoryRepository.save(convertToEntity(dto));
        return convertToDTO(inventory);
    }

    /**
     * Updates an existing inventory item by ID.
     *
     * @param id  inventory ID to update
     * @param dto inventory data with updated values
     * @return updated InventoryDTO
     * @throws RuntimeException if inventory with given ID not found
     */
    @Override
    public InventoryDTO updateInventory(Long id, InventoryDTO dto) {
        Inventory inventory = inventoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + id));

        inventory.setItemName(dto.getItemName());
        inventory.setQuantity(dto.getQuantity());
        inventory.setUnit(dto.getUnit());
        inventory.setPricePerUnit(dto.getPricePerUnit());

        Inventory updatedInventory = inventoryRepository.save(inventory);
        return convertToDTO(updatedInventory);
    }

    /**
     * Retrieves inventory item by ID.
     *
     * @param id inventory ID
     * @return InventoryDTO for the found inventory
     * @throws RuntimeException if inventory with given ID not found
     */
    @Override
    public InventoryDTO getInventoryById(Long id) {
        return inventoryRepository.findById(id)
            .map(this::convertToDTO)
            .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + id));
    }

    /**
     * Retrieves all inventory items.
     *
     * @return list of all InventoryDTOs
     */
    @Override
    public List<InventoryDTO> getAllInventory() {
        return inventoryRepository.findAll()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    /**
     * Deletes inventory item by ID.
     *
     * @param id inventory ID to delete
     */
    @Override
    public void deleteInventory(Long id) {
        inventoryRepository.deleteById(id);
    }
}
