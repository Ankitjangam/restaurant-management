package com.restaurant.restaurant_management.securityConfig;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
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

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Auth endpoints public
                        .requestMatchers("/api/auth/**").permitAll()

                        // Admin only
                        .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")

                        // Staff & Admin
                        .requestMatchers("/api/staff/**").hasAnyAuthority("ROLE_STAFF", "ROLE_ADMIN")

                        // Customer, Staff, Admin
                        .requestMatchers("/api/customer/**").hasAnyAuthority("ROLE_CUSTOMER", "ROLE_STAFF", "ROLE_ADMIN")

                        // Menu items CRUD
                        .requestMatchers(HttpMethod.GET, "/api/menu-items/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/menu-items/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/api/menu-items/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api/menu-items/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")

                        // Tables management
                        .requestMatchers(HttpMethod.POST, "/api/tables/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/tables/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/tables/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/tables/**").hasAuthority("ROLE_ADMIN")

                        // Bookings
                        .requestMatchers(HttpMethod.POST, "/api/bookings").hasAnyAuthority("ROLE_CUSTOMER", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/bookings/my").hasAuthority("ROLE_CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/api/bookings/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_STAFF")
                        .requestMatchers(HttpMethod.GET, "/api/bookings").hasAnyAuthority("ROLE_CUSTOMER", "ROLE_ADMIN", "ROLE_STAFF")
                        .requestMatchers(HttpMethod.DELETE, "/api/bookings/**").hasAnyAuthority("ROLE_CUSTOMER", "ROLE_ADMIN")

                        // Orders
                                // Orders
                                .requestMatchers(HttpMethod.POST, "/api/orders/place").hasAuthority("ROLE_CUSTOMER")
                                .requestMatchers(HttpMethod.GET, "/api/orders/my").hasAuthority("ROLE_CUSTOMER")
                                .requestMatchers(HttpMethod.PUT, "/api/orders/*/cancel").hasAuthority("ROLE_CUSTOMER")

                                .requestMatchers(HttpMethod.GET, "/api/orders").hasAnyAuthority("ROLE_ADMIN", "ROLE_STAFF")
                                .requestMatchers(HttpMethod.PUT, "/api/orders/*/status").hasAnyAuthority("ROLE_ADMIN", "ROLE_STAFF")


// Optional: allow DELETE for admin/staff
                                .requestMatchers(HttpMethod.DELETE, "/api/orders/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_STAFF")


//                        Categories management
                        .requestMatchers("/api/categories/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")

                        // Reports only admin
                        .requestMatchers("/api/reports/**").hasAuthority("ROLE_ADMIN")

                        // Any other request requires authentication
                        .anyRequest().authenticated()
                )

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
