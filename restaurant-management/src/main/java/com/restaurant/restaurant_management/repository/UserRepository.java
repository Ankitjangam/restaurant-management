package com.restaurant.restaurant_management.repository;
import com.restaurant.restaurant_management.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
