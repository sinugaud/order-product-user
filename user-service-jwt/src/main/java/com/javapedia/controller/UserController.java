//package com.javapedia.controller;
//
//import com.javapedia.entity.AuthRequest;
//import com.javapedia.entity.UserInfo;
//import com.javapedia.service.JwtService;
//import com.javapedia.service.impl.UserInfoService;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Set;
//
//@RestController
//@Log4j2
//@CrossOrigin("*")
//public class UserController {
//
//    @Autowired
//    private UserInfoService service;
//
//    @Autowired
//    private JwtService jwtService;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//    private Set<String> invalidatedTokens = new HashSet<>();
//
//
//    @GetMapping("/welcome")
//    public String welcome() {
//        return "Welcome this endpoint is not secure";
//    }
//
//    @PostMapping("/sign-up")
//    public String addNewUser(@RequestBody UserInfo userInfo) {
//        return service.addUser(userInfo);
//    }
//
//    @GetMapping("/user/profile")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public String userProfile() {
//        return "Welcome to User Profile";
//    }
//
//    @GetMapping("/admin/profile")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public String adminProfile() {
//        return "Welcome to Admin Profile";
//    }
//
////    @PostMapping("/generateToken")
////    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
////        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
////        if (authentication.isAuthenticated()) {
////            return jwtService.generateToken(authRequest.getUsername());
////        } else {
////            throw new UsernameNotFoundException("invalid user request !");
////        }
////    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws Exception {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
//            );
//
//            if (authentication.isAuthenticated()) {
//                String token = jwtService.generateToken(authRequest.getUsername());
//                return ResponseEntity.ok(token);
//            } else {
//                throw new UsernameNotFoundException("Invalid user request!");
//            }
//        } catch (Exception e) {
//            log.info(e.getMessage());
//            return ResponseEntity.badRequest().body("Authentication failed: " + e.getMessage());
//        }
//    }
//
//    @GetMapping("/token/validate")
//    public ResponseEntity<Boolean> isTokenValid(@RequestHeader("Authorization") String jwtToken) {
//        //1) Extract the jwtToken by removing the word "Bearer " from jwtToken string. Use substring()
//        jwtToken = jwtToken.substring(7, jwtToken.length());
//        if (invalidatedTokens.contains(jwtToken)) {
//            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
//        }
//
////        2) Extract username from the jwtToken using jwtUtils.extractUsername(jwtToken)
//        String username = jwtService.extractUsername(jwtToken);
////		System.out.println(username);
//
//        //3) Extract UserDetails using UserDetailsService.loadUserByUsername(username)
//        UserDetails userDetails = service.loadUserByUsername(username);
//
//        //4) Validate the token using jwtUtils.validateToken(jwtToken, userDetails);
//        boolean isTokenValid = jwtService.validateToken(jwtToken, userDetails);
//        if (isTokenValid)
//            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
//        else
//            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
//    }
//
//    //    @PostMapping("/logout")
//    @RequestMapping(value = "/auth/logout", method = RequestMethod.POST, headers = "Accept=application/json")
//
//    public ResponseEntity<String> logout(@RequestHeader("Authorization") String jwtToken) throws IOException {
//        log.info("Token: {}", jwtToken);
//        invalidatedTokens.add(jwtToken);
//
//        return ResponseEntity.status(HttpStatus.OK)
//                .body("Logout successful. Token invalidated. Token: " + jwtToken);
//    }
//
//    @GetMapping("/admin/logged/username")
//    public ResponseEntity<String> getUsernameFromToken(@RequestHeader("Authorization") String jwtToken) {
//
//        try {
//            jwtToken = jwtToken.substring(7);
//
//            String username = jwtService.extractUsername(jwtToken);
//
//            UserDetails userDetails = service.loadUserByUsername(username);
//
//            boolean isTokenValid = jwtService.validateToken(jwtToken, userDetails);
//
//            if (isTokenValid) {
//                return ResponseEntity.status(HttpStatus.OK)
//                        .body(username);
//            } else {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                        .body("Token is invalid");
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body("Error processing the token");
//        }
//    }
//
//    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//    public ResponseEntity<String> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
//        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
//                .body("Method not allowed for this endpoint. Supported methods: " + Arrays.toString(ex.getSupportedMethods()));
//    }
//
//
//}
