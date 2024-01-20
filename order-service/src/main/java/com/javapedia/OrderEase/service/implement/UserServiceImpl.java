    package com.javapedia.OrderEase.service.implement;

    import com.javapedia.OrderEase.controller.UserClient;
    import com.javapedia.OrderEase.service.UserService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    @Service
    public class UserServiceImpl implements UserService {
        @Autowired
        private UserClient userClient;
        @Override
        public boolean isUserLoggedIn(String token) {
            return userClient.isTokenValid(token);
        }

        @Override
        public String getUsernameFromToken(String token) {
            return userClient.getUsernameFromToken(token);
        }
    }
