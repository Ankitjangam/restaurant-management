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

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void registerUser(UserRegistrationDto registrationDto) {
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        Set<Role> assignedRoles = new HashSet<>();

        if (registrationDto.getEmail().endsWith("@admin.com")) {
            Role adminRole = roleRepository.findByName(RoleType.ADMIN)
                    .orElseThrow(() -> new RuntimeException("ADMIN role not found"));
            assignedRoles.add(adminRole);
        } else if (registrationDto.getEmail().endsWith("@staff.com")) {
            Role staffRole = roleRepository.findByName(RoleType.STAFF)
                    .orElseThrow(() -> new RuntimeException("STAFF role not found"));
            assignedRoles.add(staffRole);
        } else {
            Role customerRole = roleRepository.findByName(RoleType.CUSTOMER)
                    .orElseThrow(() -> new RuntimeException("CUSTOMER role not found"));
            assignedRoles.add(customerRole);
        }

        user.setRoles(assignedRoles);

        userRepository.save(user);
    }
}
