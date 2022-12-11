package com.example.cpme273.wlb.service;

import com.example.cpme273.wlb.dto.UserDTO;
import com.example.cpme273.wlb.dto.UserPatchDTO;
import com.example.cpme273.wlb.dto.UserSearchDTO;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {

  UserDTO create(UserDTO userRequest);

  UserDTO update(UserPatchDTO userRequest, UserDTO userDTO);

  UserDTO findById(String id);

  void delete(String id);

  List<UserDTO> findAll(UserSearchDTO userSearchDTO);
}
