package com.restaurant.restaurant_management.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a role assigned to users.
 */
@Data
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)  // Important! This tells Hibernate to map the enum as a String
    private RoleType name;


}
