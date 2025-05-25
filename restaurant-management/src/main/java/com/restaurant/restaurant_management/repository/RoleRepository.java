package com.restaurant.restaurant_management.repository;

import com.restaurant.restaurant_management.model.Role;
import com.restaurant.restaurant_management.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
     Optional<Role> findByName(RoleType name);
}
