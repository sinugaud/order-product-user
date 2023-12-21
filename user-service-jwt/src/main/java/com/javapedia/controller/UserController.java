package com.javapedia.controller;

import com.javapedia.entity.AuthRequest;
import com.javapedia.entity.UserInfo;
import com.javapedia.service.JwtService;
import com.javapedia.service.UserInfoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;
    private Set<String> invalidatedTokens = new HashSet<>();


    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return service.addUser(userInfo);
    }

    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile() {
        return "Welcome to User Profile";
    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    @GetMapping("/token/validate")
    public ResponseEntity<Boolean> isTokenValid(@RequestHeader("Authorization") String jwtToken) {

        //1) Extract the jwtToken by removing the word "Bearer " from jwtToken string. Use substring()
		jwtToken = jwtToken.substring(7, jwtToken.length());
        if (invalidatedTokens.contains(jwtToken)) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }

        //2) Extract username from the jwtToken using jwtUtils.extractUsername(jwtToken)
        String username = jwtService.extractUsername(jwtToken);
//		System.out.println(username);

        //3) Extract UserDetails using UserDetailsService.loadUserByUsername(username)
        UserDetails userDetails = service.loadUserByUsername(username);

        //4) Validate the token using jwtUtils.validateToken(jwtToken, userDetails);
        boolean isTokenValid = jwtService.validateToken(jwtToken, userDetails);
        if (isTokenValid)
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        else
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String jwtToken) throws IOException {
        invalidatedTokens.add(jwtToken); // Add token to the blacklist

        return ResponseEntity.status(HttpStatus.OK)
                .body("Logout successful. Token invalidated. Token: " + jwtToken);
    }

    @GetMapping("/admin/logged/username")
    public ResponseEntity<String> getUsernameFromToken(@RequestHeader("Authorization") String jwtToken) {

        try {
            System.out.println("jwt token from user service"+jwtToke);
            jwtToken = jwtToken.substring(7);

            String username = jwtService.extractUsername(jwtToken);

            UserDetails userDetails = service.loadUserByUsername(username);

            boolean isTokenValid = jwtService.validateToken(jwtToken, userDetails);

            if (isTokenValid) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(username);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Token is invalid");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error processing the token");
        }
    }


}
