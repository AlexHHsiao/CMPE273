package com.example.cpme273.wlb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DataExistsException extends ServiceRuntimeException {

  private ResponseEntity responseEntity;

  public DataExistsException(String entity, String id) {
    super(HttpStatus.CONFLICT, 1016, generateErrorDetails(entity, id));
  }

  public DataExistsException(String msg) {
    super(HttpStatus.NOT_FOUND, 1016, msg);
  }

  private static String generateErrorDetails(String entity, String id) {
    return String.format("Entity '%s' with identifier '%s' already existed.", entity, id);
  }
}
