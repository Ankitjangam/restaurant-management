package com.restaurant.restaurant_management.controller;

import com.restaurant.restaurant_management.dto.RestaurantTableDto;
import com.restaurant.restaurant_management.dto.TableRequestDTO;
import com.restaurant.restaurant_management.model.RestaurantTable;
import com.restaurant.restaurant_management.service.RestaurantTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/tables")
@RequiredArgsConstructor
public class RestaurantTableController {

    private final RestaurantTableService tableService;

    // ✅ Add table - Only for ADMIN
        @PostMapping
    public ResponseEntity<RestaurantTable> addTable(@RequestBody TableRequestDTO dto) {
        RestaurantTable savedTable = tableService.createTable(dto);
        return ResponseEntity.ok(savedTable);
    }


    @GetMapping("/{id}")
    public ResponseEntity<RestaurantTableDto> getTable(@PathVariable Long id) {
        return ResponseEntity.ok(tableService.getTable(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantTableDto> updateTable(@PathVariable Long id, @RequestBody RestaurantTableDto dto) {
        return ResponseEntity.ok(tableService.updateTable(id, dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTable(@PathVariable Long id) {
        tableService.deleteTable(id);
        return ResponseEntity.noContent().build();
    }
}
