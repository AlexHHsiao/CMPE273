package com.example.cpme273.wlb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServiceException extends ServiceRuntimeException {
    private ResponseEntity responseEntity;

    public InternalServiceException(String username) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, 1016, generateErrorDetails());
    }

    private static String generateErrorDetails() {
        return String.format("Internal Service Error");
    }
}

