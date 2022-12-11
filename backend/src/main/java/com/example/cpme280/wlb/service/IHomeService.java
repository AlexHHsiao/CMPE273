package com.example.cpme273.wlb.service;

import com.example.cpme273.wlb.dto.HomeDTO;
import com.example.cpme273.wlb.dto.HomePatchDTO;
import com.example.cpme273.wlb.dto.HomeSearchDTO;
import com.example.cpme273.wlb.dto.UserDTO;
import java.util.List;
import java.util.UUID;


public interface IHomeService {
    HomeDTO create(HomeDTO homeRequest);

    HomeDTO update(HomePatchDTO homeRequest, UserDTO userDTO);

    HomeDTO findById(String id);

    void delete(String id, UserDTO userDTO);

    void deleteByUserId(String id, UserDTO userDTO);

    void sendOffer(String homeID, UserDTO userDTO);

    void acceptOffer(String homeId, String userId, UserDTO ownerUserDTO);

    List<HomeDTO> findAll(HomeSearchDTO homeSearchDTO);

    List<UserDTO> findAllOffers(String homeID, UserDTO userDTO);

    int findUserHomeCount(UUID id);
}
