package com.restaurant.restaurant_management.service;

import com.restaurant.restaurant_management.dto.MenuItemRequestDTO;
import com.restaurant.restaurant_management.dto.MenuItemResponseDTO;

import java.util.List;

public interface MenuItemService {

    MenuItemResponseDTO createMenuItem(MenuItemRequestDTO dto);

    List<MenuItemResponseDTO> getAllMenuItems(Long categoryId);

    MenuItemResponseDTO getMenuItemById(Long id);

    MenuItemResponseDTO updateMenuItem(Long id, MenuItemRequestDTO dto);

    void deleteMenuItem(Long id);
}