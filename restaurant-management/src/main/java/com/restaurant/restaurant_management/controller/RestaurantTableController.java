package com.restaurant.restaurant_management.controller;

import com.restaurant.restaurant_management.dto.RestaurantTableDto;
import com.restaurant.restaurant_management.dto.TableRequestDTO;
import com.restaurant.restaurant_management.model.RestaurantTable;
import com.restaurant.restaurant_management.service.RestaurantTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing restaurant tables.
 */
@RestController
@RequestMapping("/tables")
@RequiredArgsConstructor
public class RestaurantTableController {


    @Qualifier("restaurantTableServiceImpl")
    private final RestaurantTableService tableService;

    /**
     * Add a new table.
     * Access: ADMIN only (consider adding @PreAuthorize)
     *
     * @param dto table request data
     * @return saved table entity
     */
    @PostMapping
    public ResponseEntity<RestaurantTable> addTable(@RequestBody TableRequestDTO dto) {
        RestaurantTable savedTable = tableService.createTable(dto);
        return ResponseEntity.ok(savedTable);
    }

    /**
     * Get table details by id.
     *
     * @param id table id
     * @return table details DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantTableDto> getTable(@PathVariable Long id) {
        return ResponseEntity.ok(tableService.getTable(id));
    }

    /**
     * Update table details.
     *
     * @param id  table id
     * @param dto updated table data
     * @return updated table details DTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<RestaurantTableDto> updateTable(@PathVariable Long id, @RequestBody RestaurantTableDto dto) {
        return ResponseEntity.ok(tableService.updateTable(id, dto));
    }

    /**
     * Delete a table by id.
     *
     * @param id table id
     * @return no content response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTable(@PathVariable Long id) {
        tableService.deleteTable(id);
        return ResponseEntity.noContent().build();
    }
}
