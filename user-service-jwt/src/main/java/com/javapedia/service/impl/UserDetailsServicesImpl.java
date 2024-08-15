package com.javapedia.service.impl;

import com.javapedia.dto.AuthUser;
import com.javapedia.entity.User;
import com.javapedia.exception.UserNotFoundException;
import com.javapedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Primary
public class UserDetailsServicesImpl implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    private final Set<String> invalidatedTokens = new HashSet<>();


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository
                .findByUsername(username)
                .map(AuthUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User name not found: " + username));

    }


    public Optional<User> getUserByUserName(String userName) {

        return Optional.of(userRepository.findByUsername(userName).stream().findFirst().orElseThrow());
    }

    public String updatePassword(String userId, String password) throws UserNotFoundException {

        Optional<User> existingUser = Optional.of(userRepository.findById(userId).orElseThrow());
        User user = existingUser.get();
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
        return "Password update successfully";
    }


    public Optional<User> getUserDetails(Principal principal) {
        String username = principal.getName();
        return userRepository.findByUsername(username);
    }


}
