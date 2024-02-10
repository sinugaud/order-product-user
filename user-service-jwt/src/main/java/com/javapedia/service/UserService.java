package com.javapedia.service;

import com.javapedia.entity.AuthRequest;
import com.javapedia.entity.UserInfo;

public interface UserService  {
     String addNewUser(UserInfo userInfo) ;

        String authenticateAndGetToken(AuthRequest authRequest) throws Exception;
    Boolean isTokenValid(String jwtToken);
    String logout(String jwtToken);
    String getUsernameFromToken(String jwtToken);
}
