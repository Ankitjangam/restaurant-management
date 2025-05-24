package com.restaurant.restaurant_management.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class demoController {


     @GetMapping("/")
    public String GetDetails(){
        return "Welcome to My Page";
    }
}
