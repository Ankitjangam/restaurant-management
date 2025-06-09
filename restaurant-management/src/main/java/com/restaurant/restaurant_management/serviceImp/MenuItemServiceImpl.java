package com.restaurant.restaurant_management.service;

import com.restaurant.restaurant_management.dto.MenuItemRequestDTO;
import com.restaurant.restaurant_management.dto.MenuItemResponseDTO;
import com.restaurant.restaurant_management.exception.ResourceNotFoundException;
import com.restaurant.restaurant_management.model.Category;
import com.restaurant.restaurant_management.model.MenuItem;
import com.restaurant.restaurant_management.repository.CategoryRepository;
import com.restaurant.restaurant_management.repository.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final CategoryRepository categoryRepository;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository, CategoryRepository categoryRepository) {
        this.menuItemRepository = menuItemRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public MenuItemResponseDTO createMenuItem(MenuItemRequestDTO dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));

        MenuItem menuItem = new MenuItem();
        menuItem.setName(dto.getName());
        menuItem.setDescription(dto.getDescription());
        menuItem.setPrice(dto.getPrice());
        menuItem.setCategory(category);

        MenuItem savedItem = menuItemRepository.save(menuItem);
        return mapToResponse(savedItem);
    }

    @Override
    public List<MenuItemResponseDTO> getAllMenuItems(Long categoryId) {
        List<MenuItem> items;

        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
            items = menuItemRepository.findByCategory(category);
        } else {
            items = menuItemRepository.findAll();
        }

        return items.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    @Override
    public MenuItemResponseDTO getMenuItemById(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found with id: " + id));
        return mapToResponse(menuItem);
    }

    @Override
    public MenuItemResponseDTO updateMenuItem(Long id, MenuItemRequestDTO dto) {
        MenuItem menuItem = menuItemRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found with id: " + id));

        Category category = categoryRepository.findById(dto.getCategoryId())
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));

        menuItem.setName(dto.getName());
        menuItem.setDescription(dto.getDescription());
        menuItem.setPrice(dto.getPrice());
        menuItem.setCategory(category);

        MenuItem updatedItem = menuItemRepository.save(menuItem);
        return mapToResponse(updatedItem);
    }

    @Override
    public void deleteMenuItem(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found with id: " + id));
        menuItemRepository.delete(menuItem);
    }

    private MenuItemResponseDTO mapToResponse(MenuItem menuItem) {
        return new MenuItemResponseDTO(
            menuItem.getId(),
            menuItem.getName(),
            menuItem.getDescription(),
            menuItem.getPrice(),
            menuItem.getCategory().getId(),
            menuItem.getCategory().getName()
        );
    }
}
