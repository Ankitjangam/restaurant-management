package com.restaurant.restaurant_management.controller;

import com.restaurant.restaurant_management.dto.MenuItemRequestDTO;
import com.restaurant.restaurant_management.dto.MenuItemResponseDTO;
import com.restaurant.restaurant_management.service.MenuItemService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing Menu Items.
 * Provides endpoints for CRUD operations on Menu Items,
 * including optional filtering by Category ID.
 */
@RestController
@RequestMapping("/menu-items")
public class MenuItemController {

    private static final Logger logger = LoggerFactory.getLogger(MenuItemController.class);

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    /**
     * Create a new Menu Item.
     *
     * @param dto the MenuItemRequestDTO with input data (validated).
     * @return ResponseEntity with created MenuItemResponseDTO and HTTP 201 status.
     */
    @PostMapping
    public ResponseEntity<MenuItemResponseDTO> createMenuItem(@Valid @RequestBody MenuItemRequestDTO dto) {
        logger.info("Creating MenuItem with name: {}", dto.getName());
        MenuItemResponseDTO created = menuItemService.createMenuItem(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    /**
     * Get all Menu Items, optionally filtered by categoryId.
     *
     * @param categoryId optional Category ID for filtering menu items.
     * @return list of MenuItemResponseDTO.
     */
    @GetMapping
    public ResponseEntity<List<MenuItemResponseDTO>> getAllMenuItems(@RequestParam(required = false) Long categoryId) {
        logger.info("Fetching Menu Items. Filter categoryId: {}", categoryId);
        List<MenuItemResponseDTO> list = menuItemService.getAllMenuItems(categoryId);
        return ResponseEntity.ok(list);
    }

    /**
     * Get Menu Item by its ID.
     *
     * @param id Menu Item ID.
     * @return MenuItemResponseDTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MenuItemResponseDTO> getMenuItemById(@PathVariable Long id) {
        logger.info("Fetching MenuItem with id: {}", id);
        MenuItemResponseDTO item = menuItemService.getMenuItemById(id);
        return ResponseEntity.ok(item);
    }

    /**
     * Update an existing Menu Item by ID.
     *
     * @param id  Menu Item ID.
     * @param dto the updated data.
     * @return updated MenuItemResponseDTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MenuItemResponseDTO> updateMenuItem(@PathVariable Long id, @Valid @RequestBody MenuItemRequestDTO dto) {
        logger.info("Updating MenuItem with id: {}", id);
        MenuItemResponseDTO updated = menuItemService.updateMenuItem(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete a Menu Item by ID.
     *
     * @param id Menu Item ID.
     * @return HTTP 204 No Content on successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        logger.info("Deleting MenuItem with id: {}", id);
        menuItemService.deleteMenuItem(id);
        return ResponseEntity.noContent().build();
    }
}
