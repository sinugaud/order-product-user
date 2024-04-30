package com.javapedia.OrderEase.filter;

import com.javapedia.OrderEase.service.TokenBlacklist;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Log4j2
public class JwtTokenFilter extends OncePerRequestFilter {



    @Autowired
    private TokenBlacklist tokenBlacklist;
    private static final String[] AUTH_WHITELIST = {
            "/api/auth/login","/api/auth/sign-up"
    };

    @Override
    protected void doFilterInternal( HttpServletRequest request,  HttpServletResponse response,  FilterChain filterChain) throws ServletException, IOException {
        String requestPath = request.getRequestURI();

        boolean isAuthWhitelisted = isAuthWhitelisted(requestPath);

        if (isAuthWhitelisted || !requiresToken(request)) {
            filterChain.doFilter(request, response);
        } else {
            String token = extractTokenFromRequest(request);

            if (token != null && !tokenBlacklist.isBlacklisted(token)) {

                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    }

    private boolean isAuthWhitelisted(String requestPath) {
        for (String path : AUTH_WHITELIST) {
            if (requestPath.equals(path)) {
                return true;
            }
        }
        return false;
    }

    private boolean requiresToken(HttpServletRequest request) {
        return !request.getMethod().equalsIgnoreCase("OPTIONS");
    }


    public String extractTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }

        return null;
    }
}