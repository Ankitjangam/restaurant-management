package com.restaurant.restaurant_management.service;

import com.restaurant.restaurant_management.dto.InventoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryService {
    InventoryDTO createInventory(InventoryDTO dto);
    InventoryDTO updateInventory(Long id, InventoryDTO dto);
    InventoryDTO getInventoryById(Long id);
    List<InventoryDTO> getAllInventory();
    void deleteInventory(Long id);
}
