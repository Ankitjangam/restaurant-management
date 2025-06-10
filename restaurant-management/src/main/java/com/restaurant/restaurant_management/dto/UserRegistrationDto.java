package com.restaurant.restaurant_management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for user registration data.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto {

    /**
     * Unique username for the user.
     */
    @NotBlank(message = "Username is required")
    private String username;

    /**
     * User's email address.
     * Must be a valid email format.
     */
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    /**
     * User's password.
     */
    @NotBlank(message = "Password is required")
    private String password;
}
