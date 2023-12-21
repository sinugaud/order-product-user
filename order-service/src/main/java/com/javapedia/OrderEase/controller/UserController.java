package com.javapedia.OrderEase.controller;

import com.javapedia.OrderEase.dto.AuthRequestDTO;
import com.javapedia.OrderEase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserClient userClient;


    @Autowired
    UserService userService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO authRequestDTO) {
        return new ResponseEntity<>(userClient.authenticateAndGetToken(authRequestDTO), HttpStatus.OK);
    }

    @GetMapping("/username")
    public ResponseEntity<?> getUser(@RequestHeader String  token) {
        return new ResponseEntity<>(userService.getUsernameFromToken(token), HttpStatus.OK);
    }


}
