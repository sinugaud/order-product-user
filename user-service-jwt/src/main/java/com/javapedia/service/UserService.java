package com.javapedia.service;

import com.javapedia.entity.User;

import java.security.Principal;
import java.util.Optional;

public interface UserService {
    String addNewUser(User userInfo);

//    String authenticateAndGetToken(AuthDTO.LoginRequest authRequest) throws Exception;

    Boolean isTokenValid(String jwtToken);

    String logout(String jwtToken);

    String getUsernameFromToken(String jwtToken);
     Optional<User> getUserDetails(Principal principal) ;


//    UserInfo getUserProfile(String username);

}
