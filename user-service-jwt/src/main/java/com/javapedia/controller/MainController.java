package com.javapedia.controller;

import com.javapedia.entity.AuthRequest;
import com.javapedia.entity.UserInfo;
import com.javapedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class MainController {
    @Autowired
    private UserService userService;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/sign-up")
    public String signup(@RequestBody UserInfo userInfo) {
        return userService.addNewUser(userInfo);
    }

    @GetMapping("/user/profile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile() {
        return "Welcome to User Profile";
    }

    @GetMapping("/admin/profile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }


    @PostMapping("/login")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws Exception {
        return new ResponseEntity<>(userService.authenticateAndGetToken(authRequest), HttpStatus.ACCEPTED);

    }

    @GetMapping("/token/validate")
    public ResponseEntity<Boolean> isTokenValid(@RequestHeader("Authorization") String jwtToken) {

        return new ResponseEntity<Boolean>(userService.isTokenValid(jwtToken), HttpStatus.OK);

    }

    @PostMapping("/auth/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String jwtToken) throws IOException {

        return new ResponseEntity<>(userService.logout(jwtToken), HttpStatus.OK);

    }

    @GetMapping("/admin/logged/username")
    public ResponseEntity<String> getUsernameFromToken(@RequestHeader("Authorization") String jwtToken) {
        return new ResponseEntity<>(userService.getUsernameFromToken(jwtToken), HttpStatus.OK);
    }


}
