package com.restaurant.restaurant_management.repository;

import com.restaurant.restaurant_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link User} entities.
 * Provides CRUD operations along with methods to find users by email and username.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by their email address.
     *
     * @param email the email of the user to find.
     * @return an Optional containing the found user, or empty if not found.
     */
    Optional<User> findByEmail(String email);

    /**
     * Find a user by their username.
     *
     * @param username the username of the user to find.
     * @return an Optional containing the found user, or empty if not found.
     */
    Optional<User> findByUsername(String username);
}
