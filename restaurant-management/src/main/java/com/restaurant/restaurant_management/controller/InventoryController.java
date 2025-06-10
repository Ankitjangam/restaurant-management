package com.restaurant.restaurant_management.controller;

import com.restaurant.restaurant_management.dto.InventoryDTO;
import com.restaurant.restaurant_management.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing inventory items.
 */
@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    /**
     * Create a new inventory item.
     *
     * @param dto inventory data transfer object
     * @return created InventoryDTO
     */
    @PostMapping
    public ResponseEntity<InventoryDTO> create(@RequestBody InventoryDTO dto) {
        return ResponseEntity.ok(inventoryService.createInventory(dto));
    }

    /**
     * Update an existing inventory item by ID.
     *
     * @param id  inventory item ID
     * @param dto inventory data transfer object with updated values
     * @return updated InventoryDTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<InventoryDTO> update(@PathVariable Long id, @RequestBody InventoryDTO dto) {
        return ResponseEntity.ok(inventoryService.updateInventory(id, dto));
    }

    /**
     * Get an inventory item by its ID.
     *
     * @param id inventory item ID
     * @return InventoryDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<InventoryDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.getInventoryById(id));
    }

    /**
     * Get all inventory items.
     *
     * @return list of InventoryDTO
     */
    @GetMapping
    public ResponseEntity<List<InventoryDTO>> getAll() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    /**
     * Delete an inventory item by its ID.
     *
     * @param id inventory item ID
     * @return no content response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }
}
