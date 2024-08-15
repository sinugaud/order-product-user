package com.javapedia.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/user-service-fallback")
    public String userServiceFallback() {
        return "User service is currently unavailable. Please try again later.";
    }

    @GetMapping("/order-service-fallback")
    public String orderServiceFallback() {
        return "order service is currently unavailable. Please try again later.";
    }

    @GetMapping("/product-service-fallback")
    public String productServiceFallback() {
        return "order service is currently unavailable. Please try again later.";
    }
}

