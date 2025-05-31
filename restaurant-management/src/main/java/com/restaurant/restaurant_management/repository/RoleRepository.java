package com.restaurant.restaurant_management.repository;

import com.restaurant.restaurant_management.model.Role;
import com.restaurant.restaurant_management.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
     Optional<Role> findByName(RoleType name);
}
