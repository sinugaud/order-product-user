package com.javapedia.service;

import com.javapedia.entity.User;

import java.security.Principal;
import java.util.Optional;

public interface UserService {
    String addNewUser(User user);

    String updateProfile(String userId, User user);

    Optional<User> getUserDetails(Principal principal);

    String deleteUser(String userId);

    String updatePassword(String id, String password);

}
