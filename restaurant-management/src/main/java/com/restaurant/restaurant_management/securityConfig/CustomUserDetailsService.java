package com.restaurant.restaurant_management.securityConfig;

import com.restaurant.restaurant_management.model.User;
import com.restaurant.restaurant_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Custom implementation of UserDetailsService to load user-specific data.
 * This service loads user details (email, password, roles) from the database
 * for Spring Security authentication.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Locates the user based on the email address.
     *
     * @param email the email identifying the user whose data is required.
     * @return a fully populated UserDetails object (used by Spring Security).
     * @throws UsernameNotFoundException if the user could not be found or has no GrantedAuthority.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            user.getRoles().stream()
                // Convert roles to SimpleGrantedAuthority for Spring Security
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList())
        );
    }
}
