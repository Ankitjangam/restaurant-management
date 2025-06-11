package com.restaurant.restaurant_management.controller;

import com.restaurant.restaurant_management.dto.UserRegistrationDto;
import com.restaurant.restaurant_management.securityConfig.CustomUserDetailsService;
import com.restaurant.restaurant_management.securityConfig.JwtUtil;
import com.restaurant.restaurant_management.serviceImp.CategoryServiceImp;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for user authentication endpoints: register and login.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    // User service for registration logic
    private final CategoryServiceImp.UserService userService;

    // AuthenticationManager to authenticate login credentials
    private final AuthenticationManager authenticationManager;

    // JWT utility to generate tokens after successful login
    private final JwtUtil jwtUtil;

    // Custom UserDetailsService to load user details
    private final CustomUserDetailsService userDetailsService;

    /**
     * Endpoint to register a new user.
     * Validates input and returns error messages if invalid.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRegistrationDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            // Return validation errors with 400 Bad Request
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        userService.registerUser(userDto);
        return ResponseEntity.ok("User registered successfully");
    }

    /**
     * Endpoint to login a user.
     * Authenticates credentials and returns a JWT token if successful.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
        } catch (AuthenticationException ex) {
            // Return 401 Unauthorized for invalid credentials
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(token));
    }
}

/**
 * Request DTO for authentication (login).
 */
@Data
class AuthRequest {
    private String email;
    private String password;
}

/**
 * Response DTO to return JWT token after successful authentication.
 */
@Data
@AllArgsConstructor
class AuthResponse {
    private String jwtToken;
}
