package com.javapedia.service.impl;

import com.javapedia.entity.User;
import com.javapedia.exception.UserNotFoundException;
import com.javapedia.repository.UserRepository;
import com.javapedia.service.UserService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;

    private  final  PasswordEncoder passwordEncoder;

      public UserServiceImpl(UserRepository userRepository,PasswordEncoder passwordEncoder)
      {
          this.userRepository = userRepository;
          this.passwordEncoder = passwordEncoder;
      }


    @Override
    public String addNewUser(User user) {

        try {
            Optional<User> existingUserByUsername = userRepository.findByUsername(user.getUsername());
            if (existingUserByUsername.isPresent()) {
                return "Username is already taken.";
            }

            Optional<User> existingUserByEmail = userRepository.findByEmail(user.getEmail());
            if (existingUserByEmail.isPresent()) {
                return "Email is already registered.";
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);

            return "User Added Successfully";
        } catch (ConstraintViolationException e) {
            return "Validation error: " + e.getMessage();
        } catch (DataIntegrityViolationException e) {
            return "Database error: " + e.getMessage();
        } catch (Exception e) {
            return "An unexpected error occurred: " + e.getMessage();
        }
    }

    @Override
    public String updateProfile(String userId, User user) {
        User existingUser = userRepository.findById(userId).orElseThrow();

        existingUser.setUsername(user.getUsername());
//        existingUser.setDisplayPicture(user.getDisplayPicture());
        existingUser.setEmail(user.getEmail());
        existingUser.setRoles(user.getRoles());

        if (!user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userRepository.save(existingUser);

        return "User profile updated successfully";
    }

    @Override
    public Optional<User> getUserDetails(Principal principal) {
        return Optional.empty();
    }

    @Override
    public String deleteUser(String id) {
        User user =
                userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.delete(user);
        return "User deleted successfully";
    }

    @Override
    public String updatePassword(String id, String password) {

        Optional<User> existingUser = Optional.of(userRepository.findById(id).orElseThrow());
        User user = existingUser.get();
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
        return "Password update successfully";
    }


}
