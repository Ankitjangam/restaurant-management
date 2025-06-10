package com.restaurant.restaurant_management.service;

import com.restaurant.restaurant_management.dto.MenuItemRequestDTO;
import com.restaurant.restaurant_management.dto.MenuItemResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service interface for managing menu items.
 */
@Service
public interface MenuItemService {

    /**
     * Creates a new menu item.
     *
     * @param dto the menu item request DTO
     * @return the created menu item response DTO
     */
    MenuItemResponseDTO createMenuItem(MenuItemRequestDTO dto);

    /**
     * Retrieves all menu items optionally filtered by category ID.
     *
     * @param categoryId the category ID to filter menu items (nullable)
     * @return list of menu item response DTOs
     */
    List<MenuItemResponseDTO> getAllMenuItems(Long categoryId);

    /**
     * Retrieves a menu item by its ID.
     *
     * @param id the menu item ID
     * @return the menu item response DTO
     */
    MenuItemResponseDTO getMenuItemById(Long id);

    /**
     * Updates a menu item by its ID.
     *
     * @param id  the menu item ID
     * @param dto the menu item request DTO with updated data
     * @return the updated menu item response DTO
     */
    MenuItemResponseDTO updateMenuItem(Long id, MenuItemRequestDTO dto);

    /**
     * Deletes a menu item by its ID.
     *
     * @param id the menu item ID to delete
     */
    void deleteMenuItem(Long id);
}
