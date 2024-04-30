package com.javapedia.repository;

import com.javapedia.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);

}
