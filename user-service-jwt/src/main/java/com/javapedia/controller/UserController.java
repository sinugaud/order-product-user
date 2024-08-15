package com.javapedia.controller;

import com.javapedia.entity.User;
import com.javapedia.exception.UserNotFoundException;
import com.javapedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<String> addNewUser(@RequestBody User user) {
        try {
            String response = userService.addNewUser(user);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while adding the user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateProfile(@PathVariable String userId, @RequestBody User user) {
        try {
            String response = userService.updateProfile(userId, user);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User not found: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while updating the profile: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/details")
    public ResponseEntity<Optional<User>> getUserDetails(Principal principal) {
        try {
            Optional<User> userDetails = userService.getUserDetails(principal);
            if (userDetails.isPresent()) {
                return new ResponseEntity<>(userDetails, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Optional.empty(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Optional.empty(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") String id) {
        try {
            String result = userService.deleteUser(id);
            return ResponseEntity.ok("user deleted successfully");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
