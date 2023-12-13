package com.javapedia.UserNest.repository;

import com.javapedia.UserNest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsernameOrEmail(String username, String email);

    Boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    Boolean existsByEmail(String email);
}