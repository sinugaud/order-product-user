package com.javapedia.OrderEase.service;

public interface UserService {


    boolean isUserLoggedIn(String token);
    String getUsernameFromToken(String token);

}
