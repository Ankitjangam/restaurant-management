package com.restaurant.restaurant_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a Restaurant entity in the system.
 * Contains basic details like name, address, and description,
 * along with the list of menu items offered by the restaurant.
 * <p>
 * Uses JPA annotations for ORM mapping.
 * Relationships:
 * - One-to-many relationship with MenuItem (a restaurant has multiple menu items).
 * <p>
 * Fetch type for menuItems is LAZY to optimize performance by loading menu items only when needed.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the restaurant.
     */
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * Physical address of the restaurant.
     */
    @Column(length = 255)
    private String address;

    /**
     * Brief description or overview of the restaurant.
     */
    @Column(length = 500)
    private String description;


    @Column(length = 13)
    private String phone;

    /**
     * List of menu items available at this restaurant.
     * Cascade ALL is used so that related menu items are persisted/updated/deleted along with the restaurant.
     */
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MenuItem> menuItems;
}
