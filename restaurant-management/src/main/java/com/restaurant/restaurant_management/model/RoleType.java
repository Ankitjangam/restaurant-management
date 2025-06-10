package com.restaurant.restaurant_management.model;

/**
 * Enum representing the different roles available in the restaurant management system.
 * These roles are used for role-based access control and permissions management.
 */
public enum RoleType {
    ROLE_ADMIN,     // Administrator with full access
    ROLE_STAFF,     // Staff members with limited access to operational features
    ROLE_CUSTOMER   // Customers with access to user-specific features
}
