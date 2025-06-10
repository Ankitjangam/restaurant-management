package com.restaurant.restaurant_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Entity representing a food or drink category in the restaurant.
 * Each category can have multiple associated menu items.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    /**
     * Unique identifier for the category.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the category (e.g., Starters, Beverages, Main Course).
     * Must be unique and not null.
     */
    @Column(nullable = false, unique = true, length = 50)
    private String name;

    /**
     * Optional description providing additional context about the category.
     */
    @Column(length = 255)
    private String description;

    /**
     * List of menu items associated with this category.
     * One category can have many menu items.
     */
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MenuItem> menuItems;
}
