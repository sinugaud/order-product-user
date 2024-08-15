package com.javapedia.controller;

import com.javapedia.dto.AuthDTO;
import com.javapedia.dto.AuthUser;
import com.javapedia.entity.User;
import com.javapedia.service.TokenBlacklist;
import com.javapedia.service.UserService;
import com.javapedia.service.impl.AuthService;
import com.javapedia.service.impl.UserDetailsServicesImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserDetailsServicesImpl userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenBlacklist tokenBlacklist;

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody AuthDTO.LoginRequest userLogin) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLogin.username(), userLogin.password()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            AuthUser userDetails = (AuthUser) authentication.getPrincipal();
            log.info("Token requested for user :{}", authentication.getAuthorities());
            log.info("Authentication details :{}", authentication);

            String token = authService.generateToken(authentication);
            AuthDTO.Response response = new AuthDTO.Response("User logged in successfully", token);
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        } catch (DisabledException e) {
            return new ResponseEntity<>("User is disabled", HttpStatus.FORBIDDEN);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Exception during login :->  {}", e.getMessage());
            return new ResponseEntity<>("An unexpected error occurred during login", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> signup(@RequestBody User user) {
        try {
            String response = userService.addNewUser(user);
            if (response.equals("User Added Successfully")) {
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("Exception during signup :->  {}", e.getMessage());
            return new ResponseEntity<>(
                    "An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            tokenBlacklist.addToBlacklist(token);
            return ResponseEntity.ok("Successfully logged out");
        } catch (Exception e) {
            log.error("Exception during logout :->  {}", e.getMessage());
            return new ResponseEntity<>("An unexpected error occurred during logout", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex) {
        log.error("Global exception caught :->  {}", ex.getMessage());
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
