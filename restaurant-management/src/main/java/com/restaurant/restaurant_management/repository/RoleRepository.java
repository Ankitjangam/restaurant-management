package com.restaurant.restaurant_management.repository;

import com.restaurant.restaurant_management.enums.RoleType;
import com.restaurant.restaurant_management.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Role} entities.
 * Provides CRUD operations and custom query to find roles by name.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Find a role by its name (e.g., ROLE_ADMIN, ROLE_CUSTOMER).
     *
     * @param name The name of the role.
     * @return Optional containing the Role if found, empty otherwise.
     */
    Optional<Role> findByName(RoleType name);
}
