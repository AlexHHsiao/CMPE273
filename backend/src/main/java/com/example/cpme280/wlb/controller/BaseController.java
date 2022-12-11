package com.example.cpme273.wlb.controller;

import com.example.cpme273.wlb.data.CookieRepository;
import com.example.cpme273.wlb.data.entity.Cookie;
import com.example.cpme273.wlb.dto.Role;
import com.example.cpme273.wlb.dto.UserDTO;
import com.example.cpme273.wlb.exception.AuthorizationException;
import com.example.cpme273.wlb.exception.SessionNotFoundException;
import com.example.cpme273.wlb.service.IUserService;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

public class BaseController {

  @Autowired CookieRepository cookieRepository;
  @Autowired
  IUserService userService;

  public UserDTO restoreSession(HttpHeaders headers) {

    if (Objects.isNull(headers.get("Authorization"))) {
      throw new SessionNotFoundException();
    }
    String cookie = headers.get("Authorization").get(0);
    Optional<Cookie> cookieOptional = cookieRepository.findByCookie(cookie);
    if (!cookieOptional.isPresent()) {
      throw new SessionNotFoundException();
    }

    Cookie existingCookie = cookieOptional.get();
    Date now = new Date();
    if (now.getTime() - existingCookie.getCreated().getTime()
        > existingCookie.getExpireDuration() * 1000) {
      throw new SessionNotFoundException();
    }

    return userService.findById(existingCookie.getUserId().toString());
  }

  public UserDTO restoreAdminSession(HttpHeaders headers) {

    if (Objects.isNull(headers.get("Authorization"))) {
      throw new SessionNotFoundException();
    }
    String cookie = headers.get("Authorization").get(0);
    Optional<Cookie> cookieOptional = cookieRepository.findByCookie(cookie);
    if (!cookieOptional.isPresent()) {
      throw new SessionNotFoundException();
    }

    Cookie existingCookie = cookieOptional.get();
    Date now = new Date();
    if (now.getTime() - existingCookie.getCreated().getTime()
        > existingCookie.getExpireDuration() * 1000) {
      throw new SessionNotFoundException();
    }

    UserDTO userDTO = userService.findById(existingCookie.getUserId().toString());
    if (!Role.ADMIN.getValue().equalsIgnoreCase(userDTO.getRole())) {
      throw new AuthorizationException("admin only");
    }
    return userDTO;
  }
}
