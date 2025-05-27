package com.restaurant.restaurant_management.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "This is a public endpoint, accessible by anyone.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('STAFF') or hasRole('ADMIN')")
    public String userAccess() {
        return "This endpoint is accessible to all authenticated users.";
    }

    @GetMapping("/staff")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public String staffAccess() {
        return "This endpoint is accessible only to STAFF and ADMIN.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "This endpoint is accessible only to ADMIN.";
    }
}
