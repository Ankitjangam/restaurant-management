package com.restaurant.restaurant_management.service;

import com.restaurant.restaurant_management.dto.InventoryDTO;
import com.restaurant.restaurant_management.model.Inventory;
import com.restaurant.restaurant_management.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    private InventoryDTO convertToDTO(Inventory inventory) {
        return InventoryDTO.builder()
            .id(inventory.getId())
            .itemName(inventory.getItemName())
            .quantity(inventory.getQuantity())
            .unit(inventory.getUnit())
            .pricePerUnit(inventory.getPricePerUnit())
            .build();
    }

    private Inventory convertToEntity(InventoryDTO dto) {
        return Inventory.builder()
            .id(dto.getId())
            .itemName(dto.getItemName())
            .quantity(dto.getQuantity())
            .unit(dto.getUnit())
            .pricePerUnit(dto.getPricePerUnit())
            .build();
    }

    @Override
    public InventoryDTO createInventory(InventoryDTO dto) {
        Inventory inventory = inventoryRepository.save(convertToEntity(dto));
        return convertToDTO(inventory);
    }

    @Override
    public InventoryDTO updateInventory(Long id, InventoryDTO dto) {
        Inventory inventory = inventoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Inventory not found"));
        inventory.setItemName(dto.getItemName());
        inventory.setQuantity(dto.getQuantity());
        inventory.setUnit(dto.getUnit());
        inventory.setPricePerUnit(dto.getPricePerUnit());
        return convertToDTO(inventoryRepository.save(inventory));
    }

    @Override
    public InventoryDTO getInventoryById(Long id) {
        return inventoryRepository.findById(id)
            .map(this::convertToDTO)
            .orElseThrow(() -> new RuntimeException("Inventory not found"));
    }

    @Override
    public List<InventoryDTO> getAllInventory() {
        return inventoryRepository.findAll()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteInventory(Long id) {
        inventoryRepository.deleteById(id);
    }
}
