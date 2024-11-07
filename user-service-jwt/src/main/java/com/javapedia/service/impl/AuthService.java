package com.javapedia.service.impl;

import com.javapedia.dto.AuthUser;
import com.javapedia.service.TokenBlacklist;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Log4j2
public class AuthService {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private JwtDecoder jwtDecoder;

    @Autowired
    private TokenBlacklist tokenBlacklist;

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        List<String> authorities =
                authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        JwtClaimsSet claims =
                JwtClaimsSet.builder()
                        .issuer("self")
                        .issuedAt(now)
                        .expiresAt(now.plus(30, ChronoUnit.MINUTES))
                        .subject(authentication.getName())
                        .claim("authorities", authorities)
                        .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public boolean validateToken(String token) {
        try {
            log.info("Attempting to decode token: {}", token);

            // Decode the JWT token
            Jwt decodedJwt = jwtDecoder.decode(token);

            // Extract the token subject (typically the username or user ID)
            String subject = decodedJwt.getSubject();

            // Get the currently authenticated user from the SecurityContext
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !(authentication.getPrincipal() instanceof AuthUser)) {
                log.warn("No authenticated user found or invalid principal type.");
                return false;
            }

            AuthUser userDetails = (AuthUser) authentication.getPrincipal();
            String username = userDetails.getUsername();

            // Check if the token is in the blacklist
            if (tokenBlacklist.isBlacklisted(token)) {
                log.warn("Token is blacklisted.");
                return false;
            }

            // Validate if the username in the token matches the authenticated user's username
            if (!username.equals(subject)) {
                log.warn("Token subject does not match authenticated user.");
                return false;
            }

            // Optional: Additional claims validation (if required)
            // Example: Check expiration, roles, etc.

            log.info("Token is valid.");
            return true;

        } catch (JwtException e) {
            log.error("Invalid or expired JWT token", e);
            return false;
        } catch (Exception e) {
            log.error("Unexpected error during token validation", e);
            return false;
        }
    }


}
