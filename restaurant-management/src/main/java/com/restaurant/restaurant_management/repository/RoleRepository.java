package com.restaurant.restaurant_management.repository;

import com.restaurant.restaurant_management.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
