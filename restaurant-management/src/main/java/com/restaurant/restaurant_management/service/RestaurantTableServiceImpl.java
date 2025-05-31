package com.restaurant.restaurant_management.service;

import com.restaurant.restaurant_management.dto.RestaurantTableDto;
import com.restaurant.restaurant_management.dto.TableRequestDTO;
import com.restaurant.restaurant_management.model.RestaurantTable;
import com.restaurant.restaurant_management.repository.RestaurantTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantTableServiceImpl implements RestaurantTableService {

    private final RestaurantTableRepository tableRepository;

    @Override
    public RestaurantTable createTable(TableRequestDTO dto) {
        RestaurantTable table = RestaurantTable.builder()
                .tableNumber(dto.getTableNumber())  // keep it as String
                .capacity(dto.getCapacity())
                .available(true)
                .build();
        return tableRepository.save(table);
    }


    @Override
    public RestaurantTableDto getTable(Long id) {
        RestaurantTable table = tableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Table not found"));
        return convertToDto(table);
    }

    @Override
    public RestaurantTableDto updateTable(Long id, RestaurantTableDto dto) {
        RestaurantTable table = tableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Table not found"));

        table.setTableNumber(String.valueOf(dto.getTableNumber()));
        table.setCapacity(dto.getCapacity());
        table.setAvailable(dto.isAvailable());

        RestaurantTable updated = tableRepository.save(table);
        return convertToDto(updated);
    }

    @Override
    public void deleteTable(Long id) {
        RestaurantTable table = tableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Table not found"));
        tableRepository.delete(table);
    }

    private RestaurantTableDto convertToDto(RestaurantTable table) {
        RestaurantTableDto dto = new RestaurantTableDto();
        dto.setId(table.getId());
        dto.setTableNumber(table.getTableNumber());
        dto.setCapacity(table.getCapacity());
        dto.setAvailable(table.isAvailable());
        return dto;
    }
}