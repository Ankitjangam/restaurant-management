package com.restaurant.restaurant_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class RestaurantManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantManagementApplication.class, args);
	}

}
