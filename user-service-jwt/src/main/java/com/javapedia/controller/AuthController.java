package com.javapedia.controller;

import com.javapedia.dto.AuthDTO;
import com.javapedia.dto.AuthUser;
import com.javapedia.entity.User;
import com.javapedia.fillter.AuthService;
import com.javapedia.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;
    @Autowired
    private UserInfoService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDTO.LoginRequest userLogin) throws IllegalAccessException {
        Authentication authentication =
                authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(
                                userLogin.username(),
                                userLogin.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AuthUser userDetails = (AuthUser) authentication.getPrincipal();


        log.info("Token requested for user :{}", authentication.getAuthorities());
        String token = authService.generateToken(authentication);

        AuthDTO.Response response = new AuthDTO.Response("User logged in successfully", token);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/sign-up")
    public String signup(@RequestBody User userInfo) {
        return userDetailsService.addUser(userInfo);
    }


}
