package com.example.cpme273.wlb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AuthorizationException extends ServiceRuntimeException{
    private ResponseEntity responseEntity;

    public AuthorizationException(String operation) {
        super(HttpStatus.UNAUTHORIZED, 1016, generateErrorDetails(operation));
    }

    private static String generateErrorDetails(String operation) {
        return String.format("Authorization failed for operation: '%s'", operation);
    }
}


