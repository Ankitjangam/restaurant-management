package com.restaurant.restaurant_management.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private Double quantity;
    private String unit;
    private Double pricePerUnit;

    private LocalDateTime lastUpdated;

    @PrePersist
    @PreUpdate
    public void setTimestamps() {
        this.lastUpdated = LocalDateTime.now();
    }
}
