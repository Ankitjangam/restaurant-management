package com.restaurant.restaurant_management.service;

import com.restaurant.restaurant_management.dto.UserRegistrationDto;
import com.restaurant.restaurant_management.model.Role;
import com.restaurant.restaurant_management.model.User;
import com.restaurant.restaurant_management.repository.RoleRepository;
import com.restaurant.restaurant_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

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

        Role defaultRole = roleRepository.findByName("CUSTOMER");

        user.setRoles(Collections.singleton(defaultRole));
        userRepository.save(user);
    }
}
