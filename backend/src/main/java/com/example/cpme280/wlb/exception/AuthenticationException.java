package com.example.cpme273.wlb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AuthenticationException extends ServiceRuntimeException {
    private ResponseEntity responseEntity;

    public AuthenticationException(String username) {
        super(HttpStatus.UNAUTHORIZED, 1016, generateErrorDetails(username));
    }

    private static String generateErrorDetails(String username) {
        return String.format("Authentication failed for user: '%s'", username);
    }
}
