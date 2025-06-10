package com.restaurant.restaurant_management.serviceImp;

import com.restaurant.restaurant_management.dto.RestaurantTableDto;
import com.restaurant.restaurant_management.dto.TableRequestDTO;
import com.restaurant.restaurant_management.model.RestaurantTable;
import com.restaurant.restaurant_management.repository.RestaurantTableRepository;
import com.restaurant.restaurant_management.service.RestaurantTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantTableServiceImpl implements RestaurantTableService {

    private final RestaurantTableRepository tableRepository;

    /**
     * Create and save a new restaurant table.
     *
     * @param dto Table request data transfer object containing table details.
     * @return The saved RestaurantTable entity.
     */
    @Override
    public RestaurantTable createTable(TableRequestDTO dto) {
        RestaurantTable table = RestaurantTable.builder()
            .tableNumber(dto.getTableNumber())  // Table number stored as String
            .capacity(dto.getCapacity())
            .available(true)  // New table is available by default
            .build();
        return tableRepository.save(table);
    }

    /**
     * Retrieve a restaurant table by its ID.
     *
     * @param id The ID of the restaurant table.
     * @return The RestaurantTableDto representing the table.
     * @throws RuntimeException if table not found.
     */
    @Override
    public RestaurantTableDto getTable(Long id) {
        RestaurantTable table = tableRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Table not found"));
        return convertToDto(table);
    }

    /**
     * Update an existing restaurant table.
     *
     * @param id  The ID of the table to update.
     * @param dto DTO containing updated table details.
     * @return Updated RestaurantTableDto.
     * @throws RuntimeException if table not found.
     */
    @Override
    public RestaurantTableDto updateTable(Long id, RestaurantTableDto dto) {
        RestaurantTable table = tableRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Table not found"));

        table.setTableNumber(String.valueOf(dto.getTableNumber()));  // Ensure consistent type
        table.setCapacity(dto.getCapacity());
        table.setAvailable(dto.isAvailable());

        RestaurantTable updated = tableRepository.save(table);
        return convertToDto(updated);
    }

    /**
     * Delete a restaurant table by ID.
     *
     * @param id The ID of the table to delete.
     * @throws RuntimeException if table not found.
     */
    @Override
    public void deleteTable(Long id) {
        RestaurantTable table = tableRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Table not found"));
        tableRepository.delete(table);
    }

    /**
     * Helper method to convert RestaurantTable entity to DTO.
     *
     * @param table The RestaurantTable entity.
     * @return The RestaurantTableDto.
     */
    private RestaurantTableDto convertToDto(RestaurantTable table) {
        RestaurantTableDto dto = new RestaurantTableDto();
        dto.setId(table.getId());
        dto.setTableNumber(table.getTableNumber());
        dto.setCapacity(table.getCapacity());
        dto.setAvailable(table.isAvailable());
        return dto;
    }
}
