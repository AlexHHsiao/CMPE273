package com.example.cpme273.wlb.service.impl;

import com.example.cpme273.wlb.data.Page.WlbPageRequest;
import com.example.cpme273.wlb.data.Specification.UserSpecification;
import com.example.cpme273.wlb.data.UserRepository;
import com.example.cpme273.wlb.data.entity.User;
import com.example.cpme273.wlb.dto.Role;
import com.example.cpme273.wlb.dto.UserDTO;
import com.example.cpme273.wlb.dto.UserPatchDTO;
import com.example.cpme273.wlb.dto.UserSearchDTO;
import com.example.cpme273.wlb.exception.DataExistsException;
import com.example.cpme273.wlb.exception.DataNotFoundException;
import com.example.cpme273.wlb.service.IUserService;
import com.example.cpme273.wlb.utility.CommonUtil;
import com.example.cpme273.wlb.utility.EmailSender;
import com.example.cpme273.wlb.utility.PasswordEncryptor;
import com.example.cpme273.wlb.WlbConstant;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements IUserService {

  @Autowired private ObjectMapper objectMapper;
  @Autowired private UserRepository userRepository;
  @Autowired private EmailSender emailSender;
  @Autowired private UserSpecification userSpecification;
  private static final String USER = "user";

  public UserDTO create(UserDTO userDTO) {
    log.info("{} create user {}", WlbConstant.LOGGER_MSG, WlbConstant.LOGGER_START);
    UserDTO existingEntity = null;
    try {
      existingEntity = findByUsername(userDTO.getUsername());
    } catch (DataNotFoundException ex) {

    }
    if (Objects.nonNull(existingEntity)) {
      log.error("User already exist for username: {}", userDTO.getUsername());
      throw new DataExistsException("username", userDTO.getUsername());
    }

    User newEntity = createFromDTO(userDTO);
    User savedEntity = userRepository.save(newEntity);

    String emailText = String.format("Hi %s,\n\nYour new user registration is pending for admin approval.\n\nTeam WLB", userDTO.getUsername());
    emailSender.sendEmail(savedEntity.getEmail(), "HomeFinder-WLB: User Registration", emailText);

    log.info("{} create user {}", WlbConstant.LOGGER_MSG, WlbConstant.LOGGER_END);

    return convertToDTO(savedEntity);
  }

  @Override
  public UserDTO update(UserPatchDTO userRequest, UserDTO userDTO) {

    User existed = getById(userRequest.getId().toString());
    boolean before = existed.getIsActive();

    CommonUtil.copyNonNullProperties(userRequest, existed, "id", "username", "role");

    User updatedUser = userRepository.saveAndFlush(existed);

    adminActiveUser(userDTO, before, updatedUser.getIsActive(), existed);

    return convertToDTO(updatedUser);
  }

  private void adminActiveUser(UserDTO userDTO, boolean before, boolean after, User userTobeUpdated) {
    if (Role.ADMIN.getValue().equalsIgnoreCase(userDTO.getRole()) && !before && after) {
      emailSender.sendEmail(userTobeUpdated.getEmail(), "HomeFinder-WLB: User Activation", String.format("Hello %s,\n\nCongratulation! " +
              "Your account has been activated. Please go check it out!\n\nTeam WLB", userTobeUpdated.getUsername()));
    }
  }

  public void delete(String userId) {

    log.debug("{}delete {}", WlbConstant.LOGGER_MSG, WlbConstant.LOGGER_START);
    UserDTO userDTO = findById(userId);
    userRepository.deleteById(userDTO.getId());
    emailSender.sendEmail(userDTO.getEmail(), "HomeFinder-WLB: User Deactivation", String.format("Hello %s,\n\n" +
            "I'm sorry to let you know that your account has been removed.\n\nTeam WLB", userDTO.getUsername()));

    log.debug("{}delete {}", WlbConstant.LOGGER_MSG, WlbConstant.LOGGER_END);
  }

  @Override
  public List<UserDTO> findAll(UserSearchDTO userSearchDTO) {

    WlbPageRequest userPageRequest = buildUserSearchPageRequest(userSearchDTO);

    buildWhereClauseMap(userSearchDTO);

    Page<User> userPage = userRepository.findAll(userSpecification, userPageRequest);

    List<UserDTO> userDTOList = new ArrayList<>();

    userPage
        .getContent()
        .forEach(
            user -> {
              userDTOList.add(convertToDTO(user));
            });

    userSearchDTO.setCount(userPage.getTotalElements());
    return userDTOList;
  }

  private void buildWhereClauseMap(UserSearchDTO userSearchDTO) {

    Map<String, Object> userSearchMap = objectMapper.convertValue(userSearchDTO, Map.class);

    userSearchMap.remove("start");
    userSearchMap.remove("limit");
    userSearchMap.remove("sort");
    userSpecification.setWhereClauseFields(userSearchMap);
  }

  public UserDTO findById(String id) {
    log.info("Start of find user");

    Optional<User> entityOptional = userRepository.findById(UUID.fromString(id));

    if (!entityOptional.isPresent()) {
      throw new DataNotFoundException(USER, id);
    }

    return convertToDTO(entityOptional.get());
  }

  private User getById(String id) {
    Optional<User> entityOptional = userRepository.findById(UUID.fromString(id));

    if (!entityOptional.isPresent()) {
      throw new DataNotFoundException(USER, id);
    }
    return entityOptional.get();
  }

  public UserDTO findByUsername(String username) {
    log.info("Start of findByUsername");

    Optional<User> entityOptional = userRepository.findByUsername(username);

    if (!entityOptional.isPresent()) {
      throw new DataNotFoundException(USER, username);
    }

    return convertToDTO(entityOptional.get());
  }
  /** Create Transformation entity from Transformation request object */
  private User createFromDTO(UserDTO dto) {
    log.info("Start of createFromDTO Method");

    User entity = new User();
    BeanUtils.copyProperties(dto, entity);
    try {
      entity.setPassword(PasswordEncryptor.encrypt(dto.getPassword()));
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    entity.setRole(Role.valueOf(dto.getRole()));

    log.debug("Transformation entity created from Transformation request object {}", entity);

    return entity;
  }

  private UserDTO convertToDTO(User entity) {
    log.info("Start of convertToDTO Method");

    UserDTO dto = new UserDTO();
    BeanUtils.copyProperties(entity, dto, "password");
    dto.setRole(entity.getRole().name());

    log.info("End of convertToDTO Method");
    return dto;
  }

  private WlbPageRequest buildUserSearchPageRequest(UserSearchDTO userSearchDTO) {

    int start =
        (StringUtils.isEmpty(userSearchDTO.getStart())
            ? 0
            : Integer.parseInt(userSearchDTO.getStart()));
    int limit =
        (StringUtils.isEmpty(userSearchDTO.getLimit())
            ? 25
            : Integer.parseInt(userSearchDTO.getLimit()));

    return new WlbPageRequest(start, limit, Sort.by(Direction.ASC, "username"));
  }

  protected String[] getIgnoreProperties(Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
    Set<String> nullPropertyNames = new HashSet<String>();
    for (java.beans.PropertyDescriptor pd : pds) {
      Object srcValue = src.getPropertyValue(pd.getName());
      if (srcValue == null) nullPropertyNames.add(pd.getName());
    }
    String[] result = new String[nullPropertyNames.size()];
    return nullPropertyNames.toArray(result);
  }
}
