package com.restaurant.restaurant_management.securityConfig;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security configuration for JWT-based authentication and role-based authorization.
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Configure HTTP security for request authorization and filter chains.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF as we use JWT (stateless authentication)
                .csrf(AbstractHttpConfigurer::disable)

                // Define authorization rules for endpoints
                .authorizeHttpRequests(auth -> auth
                        // Public access: Authentication endpoints
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/auth/**").permitAll()

                        // Role-based access control

                        // Admin only endpoints
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")

                        // Staff and Admin access
                        .requestMatchers("/staff/**").hasAnyAuthority("ROLE_STAFF", "ROLE_ADMIN")

                        // Customer, Staff, Admin access
                        .requestMatchers("/customer/**").hasAnyAuthority("ROLE_CUSTOMER", "ROLE_STAFF", "ROLE_ADMIN")

                        .requestMatchers("/restaurants/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_STAFF")

                        // Menu Items - read is public, write operations restricted to Admin/Manager
                        .requestMatchers(HttpMethod.GET, "/menu-items/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/menu-items/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_STAFF")
                        .requestMatchers(HttpMethod.PUT, "/menu-items/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_STAFF")
                        .requestMatchers(HttpMethod.DELETE, "/menu-items/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_STAFF")

                        // Tables management (CRUD) - Admin only
                        .requestMatchers(HttpMethod.POST, "/tables/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/tables/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/tables/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/tables/**").hasAuthority("ROLE_ADMIN")

                        // Bookings - mixed access
                        .requestMatchers(HttpMethod.POST, "/bookings").hasAnyAuthority("ROLE_CUSTOMER", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/bookings/my").hasAuthority("ROLE_CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/bookings/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_STAFF")
                        .requestMatchers(HttpMethod.GET, "/bookings").hasAnyAuthority("ROLE_CUSTOMER", "ROLE_ADMIN", "ROLE_STAFF")
                        .requestMatchers(HttpMethod.DELETE, "/bookings/**").hasAnyAuthority("ROLE_CUSTOMER", "ROLE_ADMIN")

                        // Orders - customer and staff/admin roles
                        .requestMatchers(HttpMethod.POST, "/orders/place").hasAuthority("ROLE_CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/orders/my").hasAuthority("ROLE_CUSTOMER")
                        .requestMatchers(HttpMethod.PUT, "/orders/*/cancel").hasAuthority("ROLE_CUSTOMER")

                        .requestMatchers(HttpMethod.GET, "/orders").hasAnyAuthority("ROLE_ADMIN", "ROLE_STAFF")
                        .requestMatchers(HttpMethod.PUT, "/orders/*/status").hasAnyAuthority("ROLE_ADMIN", "ROLE_STAFF")

                        // Optional: Delete orders - admin and staff only
                        .requestMatchers(HttpMethod.DELETE, "/orders/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_STAFF")

                        // Billing - staff/admin for post, all roles for get
                        .requestMatchers(HttpMethod.POST, "/billing/**").hasAnyAuthority("ROLE_STAFF", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/billing/**").hasAnyAuthority("ROLE_STAFF", "ROLE_ADMIN", "ROLE_CUSTOMER")

                        // Inventory - admin and staff only
                        .requestMatchers("/inventory/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_STAFF")

                        // Orders filter - admin only
                        .requestMatchers("/orders/filter").hasAuthority("ROLE_ADMIN")

                        // Categories management - admin and manager
                        .requestMatchers(HttpMethod.GET, "/categories/**")
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_STAFF", "ROLE_CUSTOMER")

                        // POST, PUT, DELETE allowed only for ADMIN, STAFF
                        .requestMatchers(HttpMethod.POST, "/categories/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_STAFF")
                        .requestMatchers(HttpMethod.PUT, "/categories/**")
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_STAFF")
                        .requestMatchers(HttpMethod.DELETE, "/categories/**")
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_STAFF")

                        // Reports - admin only
                        .requestMatchers("/reports/**").hasAuthority("ROLE_ADMIN")

                        // All other requests require authentication
                        .anyRequest().authenticated()
                )

                // Stateless session management for JWT
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Add JWT filter before Spring Security's UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Authentication provider that uses DAO-based authentication with BCrypt password encoding.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Expose AuthenticationManager bean.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Password encoder bean using BCrypt hashing algorithm.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
