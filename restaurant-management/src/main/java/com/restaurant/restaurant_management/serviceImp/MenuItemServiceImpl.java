package com.restaurant.restaurant_management.serviceImp;

import com.restaurant.restaurant_management.dto.MenuItemRequestDTO;
import com.restaurant.restaurant_management.dto.MenuItemResponseDTO;
import com.restaurant.restaurant_management.exception.ResourceNotFoundException;
import com.restaurant.restaurant_management.model.Category;
import com.restaurant.restaurant_management.model.MenuItem;
import com.restaurant.restaurant_management.model.Restaurant;
import com.restaurant.restaurant_management.repository.CategoryRepository;
import com.restaurant.restaurant_management.repository.MenuItemRepository;
import com.restaurant.restaurant_management.repository.RestaurantRepository;
import com.restaurant.restaurant_management.service.MenuItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing Menu Items.
 */
@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final CategoryRepository categoryRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository, CategoryRepository categoryRepository, RestaurantRepository restaurantRepository) {
        this.menuItemRepository = menuItemRepository;
        this.categoryRepository = categoryRepository;
        this.restaurantRepository = restaurantRepository;
    }

    /**
     * Creates a new MenuItem.
     *
     * @param dto DTO containing new menu item data
     * @return MenuItemResponseDTO representing created menu item
     * @throws ResourceNotFoundException if category with provided ID does not exist
     */
    @Override
    public MenuItemResponseDTO createMenuItem(MenuItemRequestDTO dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));

        Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + dto.getRestaurantId()));

        MenuItem menuItem = new MenuItem();
        return getMenuItemResponseDTO(dto, category, restaurant, menuItem);
    }

    private MenuItemResponseDTO getMenuItemResponseDTO(MenuItemRequestDTO dto, Category category, Restaurant restaurant, MenuItem menuItem) {
        menuItem.setName(dto.getName());
        menuItem.setDescription(dto.getDescription());
        menuItem.setPrice(dto.getPrice());
        menuItem.setCategory(category);
        menuItem.setRestaurant(restaurant);  // Now restaurant is resolved properly
        MenuItem savedItem = menuItemRepository.save(menuItem);
        return mapToResponse(savedItem);
    }


    /**
     * Retrieves all menu items, optionally filtered by category.
     *
     * @param categoryId optional category ID to filter menu items
     * @return list of MenuItemResponseDTOs matching criteria
     * @throws ResourceNotFoundException if category with provided ID does not exist when filtering
     */
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

    /**
     * Retrieves a MenuItem by its ID.
     *
     * @param id menu item ID
     * @return MenuItemResponseDTO for found menu item
     * @throws ResourceNotFoundException if menu item with given ID does not exist
     */
    @Override
    public MenuItemResponseDTO getMenuItemById(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found with id: " + id));
        return mapToResponse(menuItem);
    }

    /**
     * Updates an existing MenuItem.
     *
     * @param id  menu item ID to update
     * @param dto updated menu item data
     * @return updated MenuItemResponseDTO
     * @throws ResourceNotFoundException if menu item or category not found
     */
    @Override
    public MenuItemResponseDTO updateMenuItem(Long id, MenuItemRequestDTO dto) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found with id: " + id));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));

        Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + dto.getRestaurantId()));

        return getMenuItemResponseDTO(dto, category, restaurant, menuItem);
    }


    /**
     * Deletes a MenuItem by ID.
     *
     * @param id menu item ID to delete
     * @throws ResourceNotFoundException if menu item with given ID does not exist
     */
    @Override
    public void deleteMenuItem(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found with id: " + id));
        menuItemRepository.delete(menuItem);
    }

    /**
     * Maps MenuItem entity to MenuItemResponseDTO.
     *
     * @param menuItem entity to map
     * @return DTO representing the menu item
     */
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
