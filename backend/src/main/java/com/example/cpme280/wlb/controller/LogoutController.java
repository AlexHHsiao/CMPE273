package com.example.cpme273.wlb.controller;

import com.example.cpme273.wlb.service.impl.LogoutService;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(LogoutController.PATH)
public class LogoutController extends BaseController {

  private static final int VERSION = 1;
  static final String PATH = "/v" + VERSION + "/logout";

  @Autowired private LogoutService logOutService;

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping
  public ResponseEntity<Void> logout(@RequestHeader HttpHeaders headers) {

    logOutService.logout(
        Objects.nonNull(headers.get("Authorization")) ? headers.get("Authorization").get(0) : "");
    return ResponseEntity.noContent().build();
  }
}
