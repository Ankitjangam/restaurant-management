package com.restaurant.restaurant_management.service;

import com.restaurant.restaurant_management.dto.UserRegistrationDto;
import com.restaurant.restaurant_management.model.Role;
import com.restaurant.restaurant_management.model.RoleType;
import com.restaurant.restaurant_management.model.User;
import com.restaurant.restaurant_management.repository.RoleRepository;
import com.restaurant.restaurant_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Service class responsible for managing user registration and related operations.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Registers a new user based on the provided registration data transfer object.
     * The user's password is encrypted before saving, and roles are assigned based
     * on the email domain suffix.
     *
     * @param registrationDto the DTO containing user registration information
     * @throws RuntimeException if the required role is not found in the database
     */
    public void registerUser(UserRegistrationDto registrationDto) {
        // Create a new User entity and set basic details
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());

        // Encrypt the plain text password before saving
        String encodedPassword = passwordEncoder.encode(registrationDto.getPassword());
        user.setPassword(encodedPassword);

        // Determine user roles based on email domain
        Set<Role> assignedRoles = new HashSet<>();
        String email = registrationDto.getEmail();

        if (email.endsWith("@admin.com")) {
            // Assign ADMIN role if email ends with @admin.com
            Role adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("ADMIN role not found"));
            assignedRoles.add(adminRole);
        } else if (email.endsWith("@staff.com")) {
            // Assign STAFF role if email ends with @staff.com
            Role staffRole = roleRepository.findByName(RoleType.ROLE_STAFF)
                    .orElseThrow(() -> new RuntimeException("STAFF role not found"));
            assignedRoles.add(staffRole);
        } else {
            // Assign CUSTOMER role by default
            Role customerRole = roleRepository.findByName(RoleType.ROLE_CUSTOMER)
                    .orElseThrow(() -> new RuntimeException("CUSTOMER role not found"));
            assignedRoles.add(customerRole);
        }

        // Set the roles on the user entity
        user.setRoles(assignedRoles);

        // Persist the new user in the database
        userRepository.save(user);
    }
}
