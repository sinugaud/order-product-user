package com.javapedia.OrderEase.controller;

import com.javapedia.OrderEase.dto.AuthRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "USER-SERVICE", url = "http://localhost:8083/auth/")
public interface UserClient {

    @GetMapping("/welcome")
    String welcome();


    @GetMapping("/user/userProfile")
    String userProfile();


    @GetMapping("/admin/adminProfile")
    String adminProfile();


    @PostMapping("/generateToken")
    String authenticateAndGetToken(@RequestBody AuthRequestDTO authRequest);


    @GetMapping("/token/validate")
    public Boolean isTokenValid(@RequestHeader("Authorization") String jwtToken);

    @GetMapping("/admin/logged/username")
    public String getUsernameFromToken(@RequestHeader("Authorization") String jwtToken);


}
