package com.javapedia.controller;

import com.javapedia.entity.User;
import com.javapedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

@RestController
//@CrossOrigin(origins = "*")
@CrossOrigin(origins = "*",maxAge = 3600)

public class MainController {
    @Autowired
    private UserService userService;

    @GetMapping("/welcome")
//    @CrossOrigin(origins = "http://localhost:3000")

    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/sign-up")
//    @CrossOrigin(origins = "http://localhost:3000")

    public String signup(@RequestBody User userInfo) {
        return userService.addNewUser(userInfo);
    }

    @GetMapping("/user/profile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
//    @CrossOrigin(origins = "http://localhost:3000")

    public String userProfile() {
        return "Welcome to User Profile";
    }

//    @GetMapping("/admin/profile")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
////    @CrossOrigin(origins = "http://localhost:3000")
//
//    public UserInfo adminProfile(@RequestHeader("Authorization") String token) {
//        return userService.getUserProfile(token);
//
//    }


//    @PostMapping("/login")
////  @CrossOrigin(origins = "http://localhost:3000")
//    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthDTO. authRequest) throws Exception {
//        return new ResponseEntity<>(userService.authenticateAndGetToken(authRequest), HttpStatus.ACCEPTED);
//
//    }

    @GetMapping("/token/validate")
//    @CrossOrigin(origins = "http://localhost:3000")

    public ResponseEntity<Boolean> isTokenValid(@RequestHeader("Authorization") String jwtToken) {

        return new ResponseEntity<Boolean>(userService.isTokenValid(jwtToken), HttpStatus.OK);

    }

    @PostMapping("/api/auth/logout")
//    @CrossOrigin(origins = "http://localhost:3000")

    public ResponseEntity<String> logout(@RequestHeader("Authorization") String jwtToken) throws IOException {

        return new ResponseEntity<>(userService.logout(jwtToken), HttpStatus.OK);

    }

        @GetMapping("/admin/logged/username")
//    @CrossOrigin(origins = "http://localhost:3000")

    public ResponseEntity<String> getUsernameFromToken(@RequestHeader("Authorization") String jwtToken) {
        return new ResponseEntity<>(userService.getUsernameFromToken(jwtToken), HttpStatus.OK);
    }

    @GetMapping("/profile")
     @CrossOrigin(origins = "http://localhost:3000")

    public Optional<User> userProfile(Principal principal) {
        return userService.getUserDetails(principal);


    }


}
