package com.javapedia.dto;

import lombok.*;
import org.springframework.security.core.Authentication;

@Data
@AllArgsConstructor
//@NoArgsConstructor
public class   AuthDTO {

    public record LoginRequest(String username, String password) {

    }

    public record Response(String message, String token) {
    }
}
