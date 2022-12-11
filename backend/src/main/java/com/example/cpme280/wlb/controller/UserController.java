package com.example.cpme273.wlb.controller;

import com.example.cpme273.wlb.data.SearchRepository;
import com.example.cpme273.wlb.data.entity.Search;
import com.example.cpme273.wlb.dto.UserDTO;
import com.example.cpme273.wlb.dto.UserPatchDTO;
import com.example.cpme273.wlb.dto.UserSearchDTO;
import com.example.cpme273.wlb.service.IHomeService;
import com.example.cpme273.wlb.service.IUserService;
import com.example.cpme273.wlb.validator.UserValidator;
import java.net.URI;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequestMapping(UserController.PATH)
@Validated
public class UserController extends BaseController {

  private static final int VERSION = 1;
  static final String PATH = "/v" + VERSION + "/users";
  @Autowired IUserService userService;
  @Autowired UserValidator userValidator;
  @Autowired
  IHomeService homeService;
  @Autowired
  SearchRepository searchRepository;

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDTO> create(
      @ApiIgnore @RequestHeader HttpHeaders httpHeaders, @Validated @RequestBody UserDTO userDTO) {

    UserDTO userCreateResponse = userService.create(userDTO);
    Search s = new Search().builder().userId(userCreateResponse.getId())
            .home_list(new LinkedHashSet<>())
            .search_list(new LinkedHashSet<>()).build();
    searchRepository.save(s);
    URI location = buildResourceURI(userCreateResponse.getId().toString());
    return ResponseEntity.created(location).body(userCreateResponse);
  }

  @ResponseStatus(HttpStatus.OK)
  @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "{id}")
  public ResponseEntity<UserDTO> patch(
      @ApiIgnore @RequestHeader HttpHeaders httpHeaders,
      @PathVariable("id") String id,
      @Validated @RequestBody UserPatchDTO userPatchRequest) {
    UserDTO userDTO = restoreSession(httpHeaders);
    userPatchRequest.setId(UUID.fromString(id));
    UserDTO userPatchResponse = userService.update(userPatchRequest, userDTO);

    return ResponseEntity.ok(userPatchResponse);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "{id}")
  public ResponseEntity<UserDTO> find(
      @ApiIgnore @RequestHeader HttpHeaders httpHeaders, @PathVariable("id") String id) {
    restoreSession(httpHeaders);
    UserDTO userDto = userService.findById(id.toLowerCase());
    return ResponseEntity.ok(userDto);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping(value = "{id}")
  public ResponseEntity<Void> delete(
      @ApiIgnore @RequestHeader HttpHeaders httpHeaders, @PathVariable("id") String id) {
    UserDTO userDTO = restoreSession(httpHeaders);
    userService.delete(id);
    homeService.deleteByUserId(id, userDTO);
    searchRepository.deleteById(UUID.fromString(id));
    return ResponseEntity.noContent().build();
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<UserDTO>> findAll(
      @ApiIgnore @RequestHeader HttpHeaders headers,
      @Validated UserSearchDTO userSearchDTO,
      HttpServletResponse response) {
    restoreAdminSession(headers);
    List<UserDTO> userDTOList = userService.findAll(userSearchDTO);
    response.addHeader(
        "total",
        Objects.nonNull(userSearchDTO.getCount()) ? userSearchDTO.getCount().toString() : "0");
    return new ResponseEntity(userDTOList, HttpStatus.OK);
  }

  private URI buildResourceURI(String id) {
    return URI.create(PATH + "/" + id);
  }

  @InitBinder(value = "userDTO")
  void initBinderCreateUser(final ServletRequestDataBinder binder) {
    binder.addValidators(userValidator);
  }
  //  @InitBinder(value = "userPatchDTO")
  //  void initBinderPatchUser(final ServletRequestDataBinder binder) {
  //    binder.addValidators(userValidator);
  //  }
}
