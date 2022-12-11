package com.example.cpme273.wlb.service;

import com.example.cpme273.wlb.dto.UserCredentialDTO;
import com.example.cpme273.wlb.dto.UserDTO;

import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpSession;

public interface ILoginService {

  UserDTO login(HttpSession httpSession, UserCredentialDTO userCredentialDTO)
      throws NoSuchAlgorithmException;
}
