package com.javapedia.service.impl;

import com.javapedia.entity.User;
import com.javapedia.fillter.AuthService;
import com.javapedia.repository.UserInfoRepository;
import com.javapedia.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Log4j2
public class UserServiceImpl implements UserService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserInfoService service;

    @Autowired
//    private JwtService jwtService;

    private AuthService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    private Set<String> invalidatedTokens = new HashSet<>();

    @Override
    public String addNewUser(User userInfo) {
        return service.addUser(userInfo);
    }

//    @Override
//    public String authenticateAndGetToken(@RequestBody AuthDTO.LoginRequest authRequest) throws Exception {
//        try {
//            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
//
//            if (authentication.isAuthenticated()) {
//                return jwtService.generateToken(authRequest.getUsername());
//
//            } else {
//                throw new UsernameNotFoundException("Invalid user request!");
//            }
//        } catch (Exception e) {
//            return "Authentication failed: " + e.getMessage();
//        }
//    }

    @Override
    public Boolean isTokenValid(String jwtToken) {
        //1) Extract the jwtToken by removing the word "Bearer " from jwtToken string. Use substring()
        jwtToken = jwtToken.substring(7, jwtToken.length());
        if (invalidatedTokens.contains(jwtToken)) {
            return false;
        }

        String username = jwtService.extractUsername(jwtToken);

        UserDetails userDetails = service.loadUserByUsername(username);

        boolean isTokenValid = jwtService.validateToken(jwtToken, userDetails);
        if (isTokenValid) return true;
        else return false;
    }

    @Override
    public String logout(String jwtToken) {
        invalidatedTokens.add(jwtToken);
        return "Logout successful ";
    }

    @Override
    public String getUsernameFromToken(String jwtToken) {
        log.info("Jwt token : {}", jwtToken);

        try {
            jwtToken = jwtToken.substring(7);
            log.info("Jwt token sub string : {}", jwtToken);

            String username = jwtService.extractUsername(jwtToken);
            log.info("username from jwt : {}", username);


            UserDetails userDetails = service.loadUserByUsername(username);
            log.info("user details : {}", userDetails);


            boolean isTokenValid = jwtService.validateToken(jwtToken, userDetails);
            log.info("isTokenValid : {}", isTokenValid);


            if (isTokenValid) {
                log.info("isTokenValid username : {}", username);

                return username;
            } else {
                return "Token is invalid";
            }
        } catch (Exception e) {
            log.info("Exception message  : {}", e.getMessage());

            return "Error processing the token";
        }
    }

    //@Override
//    public UserInfo getUserProfile(String token) {
//    String usernameFromToken = getUsernameFromToken(token);
//    return userInfoRepository.findByUsername(usernameFromToken);
//
//    }

    public Optional<User> getUserDetails(Principal principal) {
        String username = principal.getName();
        Optional<User> existingUser = userInfoRepository.findByUsername(username);
        return existingUser;
    }


}
