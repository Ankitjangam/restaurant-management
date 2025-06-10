package com.restaurant.restaurant_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a menu item in the restaurant.
 * Each item belongs to a category and a restaurant.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menu_items")
public class MenuItem {

    /**
     * Unique identifier for the menu item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the menu item. Cannot be null and limited to 100 characters.
     */
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * Description of the menu item. Optional, up to 500 characters.
     */
    @Column(length = 500)
    private String description;

    /**
     * Price of the menu item. Cannot be null.
     */
    @Column(nullable = false)
    private Double price;

    /**
     * Category to which this menu item belongs.
     * Many menu items can belong to one category.
     * Fetch type is LAZY to optimize performance.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * Restaurant that offers this menu item.
     * Many menu items can belong to one restaurant.
     * Cannot be null.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

}
