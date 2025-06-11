package com.restaurant.restaurant_management.model;

import com.restaurant.restaurant_management.enums.RoleType;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Represents a Role in the system for role-based access control.
 * Each Role is uniquely identified by an ID and a role name.
 * <p>
 * The RoleType enum is persisted as a String in the database.
 */
@Data
@Entity
public class Role {

    /**
     * Primary key for the Role entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Role name stored as a String in the database.
     */
    @Enumerated(EnumType.STRING)
    private RoleType name;
}
