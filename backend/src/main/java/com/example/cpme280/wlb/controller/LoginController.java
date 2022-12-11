package com.example.cpme273.wlb.controller;

import static com.example.cpme273.wlb.WlbConstant.LOGGER_START;

import com.example.cpme273.wlb.dto.UserCredentialDTO;
import com.example.cpme273.wlb.dto.UserDTO;
import com.example.cpme273.wlb.exception.AuthenticationException;
import com.example.cpme273.wlb.service.impl.LoginService;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequestMapping(LoginController.PATH)
public class LoginController extends BaseController{

  private static final int VERSION = 1;
  static final String PATH = "/v" + VERSION + "/login";

  @Autowired private LoginService logInService;

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PostMapping(
          consumes = MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDTO> login(
          @ApiIgnore HttpSession httpSession,
          @Validated @RequestBody UserCredentialDTO userCredentialDTO,
          HttpServletResponse response)
      throws NoSuchAlgorithmException, AuthenticationException {

    log.info("{} Logging in", LOGGER_START);

    UserDTO userDTO = logInService.login(httpSession, userCredentialDTO);

    log.info("Successfully Logged in");

    response.addHeader("Authorization",httpSession.getId());
    return new ResponseEntity(userDTO, HttpStatus.OK);

  }
}
