package com.example.cpme273.wlb.service.impl;

import com.example.cpme273.wlb.data.CookieRepository;
import com.example.cpme273.wlb.data.UserRepository;
import com.example.cpme273.wlb.data.entity.Cookie;
import com.example.cpme273.wlb.data.entity.User;
import com.example.cpme273.wlb.dto.UserCredentialDTO;
import com.example.cpme273.wlb.dto.UserDTO;
import com.example.cpme273.wlb.exception.AuthenticationException;
import com.example.cpme273.wlb.exception.DataNotFoundException;
import com.example.cpme273.wlb.service.ILoginService;
import com.example.cpme273.wlb.service.IUserService;
import com.example.cpme273.wlb.utility.EmailSender;
import com.example.cpme273.wlb.utility.PasswordEncryptor;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class LoginService implements ILoginService {

  @Autowired private IUserService userService;
  @Autowired private UserRepository userRepository;
  @Autowired private CookieRepository cookieRepository;
  @Autowired private EmailSender emailSender;

  public UserDTO login(HttpSession httpSession, UserCredentialDTO userCredentialDTO)
      throws NoSuchAlgorithmException {

    String username = userCredentialDTO.getUsername();
    String password = userCredentialDTO.getPassword();

    Optional<User> entityOptional = userRepository.findByUsername(username);
    if (!entityOptional.isPresent()) {
      throw new DataNotFoundException("user", username);
    }

    // Validate credential
    String submittedPassword = PasswordEncryptor.encrypt(password);

    if (!submittedPassword.equals(entityOptional.get().getPassword())) {
      throw new AuthenticationException(username);
    }

    Optional<Cookie> cookieOptional = cookieRepository.findByCookie(httpSession.getId());

    cookieOptional.ifPresent(value -> cookieRepository.delete(value));
    Cookie cookie =
        Cookie.builder()
            .cookie(httpSession.getId())
            .expireDuration(86400)
            .userId(entityOptional.get().getId())
            .role(entityOptional.get().getRole().name())
            .build();

    cookieRepository.saveAndFlush(cookie);

    Optional<User> userOptional = userRepository.findById(entityOptional.get().getId());
    User user = userOptional.orElseThrow(() -> new DataNotFoundException("user", username));

    return convertToDTO(user);
  }

  private UserDTO convertToDTO(User entity) {
    log.info("Start of convertToDTO Method");

    UserDTO dto = new UserDTO();
    BeanUtils.copyProperties(entity, dto, "password");
    dto.setRole(entity.getRole().name());

    log.info("End of convertToDTO Method");
    return dto;
  }
}
