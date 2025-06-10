package com.restaurant.restaurant_management.service;

import com.restaurant.restaurant_management.model.Role;
import com.restaurant.restaurant_management.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Custom implementation of Spring Security's UserDetails
 * to integrate the application's User entity with Spring Security.
 */
public class CustomUserDetails implements UserDetails {

    private final User user;

    /**
     * Constructs a CustomUserDetails object wrapping the User entity.
     *
     * @param user the User entity to wrap
     */
    public CustomUserDetails(User user) {
        this.user = user;
    }

    /**
     * Returns the email of the user.
     *
     * @return user's email address
     */
    public String getEmail() {
        return user.getEmail();
    }

    /**
     * Returns the roles assigned to the user.
     *
     * @return set of roles
     */
    public Set<Role> getRoles() {
        return user.getRoles();
    }

    /**
     * Returns the authorities granted to the user.
     * Each Role is mapped to a GrantedAuthority with prefix "ROLE_".
     *
     * @return collection of granted authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
            .map(role -> (GrantedAuthority) () -> "ROLE_" + role.getName())
            .collect(Collectors.toSet());
    }

    /**
     * Returns the password used to authenticate the user.
     *
     * @return user's password
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Returns the username used to authenticate the user.
     * Here we use email as username.
     *
     * @return user's email as username
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * Indicates whether the user's account has expired.
     * Always returns true for this implementation (non-expiring account).
     *
     * @return true if account is non-expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked.
     * Always returns true for this implementation (non-locked account).
     *
     * @return true if account is non-locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) has expired.
     * Always returns true for this implementation (credentials non-expired).
     *
     * @return true if credentials are non-expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled.
     * Always returns true for this implementation (user enabled).
     *
     * @return true if user is enabled
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
