package com.javapedia.service;

import com.javapedia.entity.AuthRequest;
import com.javapedia.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;
    private Set<String> invalidatedTokens = new HashSet<>();

@Override
    public String addNewUser(UserInfo userInfo) {
        return service.addUser(userInfo);
    }

    @Override
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(authRequest.getUsername());

            } else {
                throw new UsernameNotFoundException("Invalid user request!");
            }
        } catch (Exception e) {
            return "Authentication failed: " + e.getMessage();
        }
    }

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

        try {
            jwtToken = jwtToken.substring(7);

            String username = jwtService.extractUsername(jwtToken);

            UserDetails userDetails = service.loadUserByUsername(username);

            boolean isTokenValid = jwtService.validateToken(jwtToken, userDetails);

            if (isTokenValid) {
                return username;
            } else {
                return "Token is invalid";
            }
        } catch (Exception e) {
            return "Error processing the token";
        }
    }


}
