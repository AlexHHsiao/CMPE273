package com.example.cpme273.wlb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class SessionNotFoundException extends ServiceRuntimeException {

  public SessionNotFoundException() {
    super(HttpStatus.UNAUTHORIZED, 1016, "No session, please login first");
  }
}
