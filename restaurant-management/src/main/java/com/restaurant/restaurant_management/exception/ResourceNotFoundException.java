package com.restaurant.restaurant_management.exception;

/**
 * Exception thrown when a requested resource (e.g., entity) is not found.
 * This is typically thrown in service or repository layers when an entity is missing.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     *
     * @param message detailed message describing the resource not found
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
