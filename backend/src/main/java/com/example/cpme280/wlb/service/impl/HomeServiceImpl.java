package com.example.cpme273.wlb.service.impl;

import com.example.cpme273.wlb.data.HomeRepository;
import com.example.cpme273.wlb.data.Page.WlbPageRequest;
import com.example.cpme273.wlb.data.SearchRepository;
import com.example.cpme273.wlb.data.Specification.HomeSpecification;
import com.example.cpme273.wlb.data.entity.Home;

import com.example.cpme273.wlb.data.entity.Search;
import com.example.cpme273.wlb.dto.HomeDTO;
import com.example.cpme273.wlb.dto.HomePatchDTO;
import com.example.cpme273.wlb.dto.HomeSearchDTO;
import com.example.cpme273.wlb.dto.Role;
import com.example.cpme273.wlb.dto.UserDTO;
import com.example.cpme273.wlb.exception.AuthorizationException;
import com.example.cpme273.wlb.exception.DataExistsException;
import com.example.cpme273.wlb.exception.DataNotFoundException;
import com.example.cpme273.wlb.service.IHomeService;
import com.example.cpme273.wlb.service.IUserService;
import com.example.cpme273.wlb.utility.CommonUtil;
import com.example.cpme273.wlb.utility.EmailSender;
import com.example.cpme273.wlb.WlbConstant;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
public class HomeServiceImpl implements IHomeService {

  @Autowired private HomeRepository homeRepository;
  @Autowired private IUserService userService;
  @Autowired private HomeSpecification homeSpecification;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private EmailSender emailSender;
  @Autowired private SearchRepository searchRepository;

  private static final String HOME = "home";
  private static final String OFFER_LIST_DIVIDER = "%";

  @Override
  public HomeDTO create(HomeDTO homeDTO) {
    log.info("{} create home", WlbConstant.LOGGER_START);

    Home newEntity = createFromDTO(homeDTO);
    Home savedEntity = homeRepository.save(newEntity);
    return convertToDTO(savedEntity);
  }

  @Override
  public HomeDTO update(HomePatchDTO homeRequest, UserDTO callerUserDTO) {
    Home existed = getById(homeRequest.getHomeId().toString());

    if(!existed.getOwnerId().equals(callerUserDTO.getId()) && !callerUserDTO.getRole().equals(Role.ADMIN.name())){
      throw new AuthorizationException("Home Update");
    }
    CommonUtil.copyNonNullProperties(homeRequest, existed, "homeId", "ownerId");
    Home updatedHome = homeRepository.saveAndFlush(existed);
    return convertToDTO(updatedHome);
  }

  @Override
  public HomeDTO findById(String id) {
    log.info("{} of find Home", WlbConstant.LOGGER_START);

    Optional<Home> entityOptional = homeRepository.findById(UUID.fromString(id));

    if (!entityOptional.isPresent()) {
      throw new DataNotFoundException(HOME, id);
    }

    return convertToDTO(entityOptional.get());
  }

  @Override
  public void delete(String id, UserDTO userDTO) {
    log.info("{}delete {}", WlbConstant.LOGGER_MSG, WlbConstant.LOGGER_START);

    Home existed = getById(id);
    if (!existed.getOwnerId().equals(userDTO.getId())
        && !userDTO.getRole().equals(Role.ADMIN.name())) {
      throw new AuthorizationException("Home Update");
    }

    HomeDTO homeDTO = convertToDTO(existed);

    List<Search> searchList = searchRepository.findAll();
    for(Search s : searchList) {
      Set<HomeDTO> list = s.getHome_list();
      if(list.remove(homeDTO)){
        s.setHome_list(list);
        searchRepository.saveAndFlush(s);
      }
    }

    homeRepository.deleteById(findById(id).getHomeId());
    log.info("{} delete {}", WlbConstant.LOGGER_MSG, WlbConstant.LOGGER_END);
  }

  @Override
  public void deleteByUserId(String id, UserDTO userDTO) {
    List<Home> homeList = homeRepository.findAllByOwnerId(UUID.fromString(id));
    log.info(homeList.toString());
    for(Home h : homeList) {
      delete(h.getHomeId().toString(), userDTO);
    }
  }

  public void sendOffer(String homeId, UserDTO userDTO) {
    log.info("SendOffer service starts");
    Home existedHome = getById(homeId);

    if (!existedHome.isAvailable()) {
      throw new DataNotFoundException(HOME, homeId);
    }

    String offerSenderId = userDTO.getId().toString();
    String existedOfferList = existedHome.getOfferList();

    //    check if user already sent offer
    if(existedOfferList != null && existedOfferList.length() > 0) {
      Set<String> offerSet = new HashSet<String>(Arrays.asList(existedOfferList.split(OFFER_LIST_DIVIDER)));
      if(offerSet.contains(offerSenderId)){
        log.info(String.format("User with id '%s' has already sent a offer", offerSenderId));
        throw new DataExistsException(String.format("User '%s' with id '%s' has already sent an offer", userDTO.getUsername(), offerSenderId));
      }
    }

    if(existedOfferList == null || existedOfferList.length() == 0)
      existedOfferList = offerSenderId;
    else
      existedOfferList = existedOfferList.concat(OFFER_LIST_DIVIDER).concat(offerSenderId);

    HomePatchDTO homePatchDTO = new HomePatchDTO();
    homePatchDTO.setOfferList(existedOfferList);

    CommonUtil.copyNonNullProperties(homePatchDTO, existedHome, "homeId", "ownerId");
    homeRepository.save(existedHome);

    UserDTO ownerUserDTO = userService.findById(existedHome.getOwnerId().toString());
    String emailText = String.format(
            "Hello %s,\n\nA new offer has appeared on your property from %s, please check it out!\n\nTeam WLB",
            ownerUserDTO.getUsername(),
            userDTO.getUsername()
    );
    emailSender.sendEmail(ownerUserDTO.getEmail(), "HomeFinder-WLB: New Offer Alert", emailText);


    log.info("SendOffer service ends");
  }

  @Override
  public void acceptOffer(String homeId, String userId, UserDTO ownerUserDTO) {
    log.info("Accept offer service starts");

    Home existedHome = getById(homeId);

    if (!existedHome.isAvailable()) {
      throw new DataNotFoundException(HOME, homeId);
    }

    String offerList = existedHome.getOfferList();
    Set<String> offerSet = new HashSet<>(Arrays.asList(offerList.split(OFFER_LIST_DIVIDER)));

    if(offerList == null || offerList.length() == 0 || !offerSet.contains(userId)) {
      log.info(String.format("User with id '%s' not found in offer list", userId));
      throw new DataNotFoundException(String.format("User with id '%s' not found in offer list", userId));
    }

    HomePatchDTO homePatchDTO = new HomePatchDTO();
    homePatchDTO.setHomeId(UUID.fromString(homeId));
    homePatchDTO.setAvailable(false);

    CommonUtil.copyNonNullProperties(homePatchDTO, existedHome, "homeId", "ownerId");
    homeRepository.save(existedHome);

    for(String offerUserId: offerSet){
      UserDTO userDTO = userService.findById(offerUserId);

      String acceptedText = String.format(
              "Congratulation %s!\n\nYour offer to %s the home has been accepted by the owner %s, please check it out!\n\nTeam WLB",
              userDTO.getUsername(), existedHome.getOfferType().getCorrespondingAction(), ownerUserDTO.getUsername()
      );
      String rejectedText = String.format(
              "Hello %s,\n\nWe are sorry to inform you that your offer to %s the home has not been accepted by the owner %s. Please check out other homes!\n\nTeam WLB",
              userDTO.getUsername(), existedHome.getOfferType().getCorrespondingAction(), ownerUserDTO.getUsername()
      );

      if(offerUserId.equals(userId)){
        emailSender.sendEmail(userDTO.getEmail(), "HomeFinder-WLB: Home Offer Accepted", acceptedText);

      }else{
        emailSender.sendEmail(userDTO.getEmail(), "HomeFinder-WLB: Home Offer Not Selected", rejectedText);
      }
    }

    log.info("Accept offer service ends");
  }

  @Override
  public List<UserDTO> findAllOffers(String homeId, UserDTO callerUserDTO) {
    log.info("Find home offer service starts");

    Home home = getById(homeId);

    if(!home.getOwnerId().equals(callerUserDTO.getId()) && !callerUserDTO.getRole().equals(Role.ADMIN.name())){
      throw new AuthorizationException("Fetch home offer list");
    }

    List<UserDTO> userDTOList = new ArrayList<>();
    String offerList = home.getOfferList();

    if (offerList != null && offerList.length() != 0) {
      String[] offerListArray = offerList.split(OFFER_LIST_DIVIDER);
      for (String offerUserId : offerListArray) {
        userDTOList.add(userService.findById(offerUserId));
      }
    }

    log.info("Find home offer service ends");
    return userDTOList;
  }


  @Override
  public List<HomeDTO> findAll(HomeSearchDTO homeSearchDTO) {
    WlbPageRequest homePageRequest = buildHomeSearchPageRequest(homeSearchDTO);

    buildWhereClauseMap(homeSearchDTO);

    log.info("starting of find all service");

    Page<Home> homePage = homeRepository.findAll(homeSpecification, homePageRequest);

    log.info("end of find all service");

    List<HomeDTO> homeDTOList = new ArrayList<>();

    homePage
        .getContent()
        .forEach(
            home -> {
              UserDTO userDTO = userService.findById(home.getOwnerId().toString());
              HomeDTO homeDTO = convertToDTO(home);
              homeDTO.setOwner(userDTO);
              homeDTOList.add(homeDTO);
            });

    homeSearchDTO.setCount(homePage.getTotalElements());
    return homeDTOList;
  }

  @Override
  public int findUserHomeCount(UUID id) {
    return homeRepository.findAllByOwnerId(id).size();
  }

  private void buildWhereClauseMap(HomeSearchDTO homeSearchDTO) {
    Map<String, Object> homeSearchMap = objectMapper.convertValue(homeSearchDTO, Map.class);

    // only search for available home
    // homeSearchMap.put("available", true);

    homeSearchMap.remove("start");
    homeSearchMap.remove("limit");
    homeSearchMap.remove("sort");
    homeSpecification.setWhereClauseFields(homeSearchMap);
  }

  private WlbPageRequest buildHomeSearchPageRequest(HomeSearchDTO homeSearchDTO) {

    int start =
        (StringUtils.isEmpty(homeSearchDTO.getStart())
            ? 0
            : Integer.parseInt(homeSearchDTO.getStart()));
    int limit =
        (StringUtils.isEmpty(homeSearchDTO.getLimit())
            ? 25
            : Integer.parseInt(homeSearchDTO.getLimit()));

    return new WlbPageRequest(start, limit, Sort.by(Direction.ASC, "price"));
  }

  private Home getById(String id) {
    Optional<Home> entityOptional = homeRepository.findById(UUID.fromString(id));

    if (!entityOptional.isPresent()) {
      throw new DataNotFoundException(HOME, id);
    }
    return entityOptional.get();
  }

  private Home createFromDTO(HomeDTO homeDTO) {
    log.info("{} of createFromDTO in {}", WlbConstant.LOGGER_START, HOME);

    Home entity = new Home();
    BeanUtils.copyProperties(homeDTO, entity, "available");

    entity.setHomeType(homeDTO.getHomeType());

    log.info("Home to be saved: {}", entity);
    log.info("{} of createFromDTO in {}", WlbConstant.LOGGER_END, HOME);

    return entity;
  }

  private HomeDTO convertToDTO(Home entity) {
    log.info("{} of convertToDTO in {}", WlbConstant.LOGGER_START, HOME);

    HomeDTO dto = new HomeDTO();
    BeanUtils.copyProperties(entity, dto);
    dto.setHomeType(entity.getHomeType());

    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
    dateFormat.setTimeZone(TimeZone.getTimeZone("PST"));
    String strDate = dateFormat.format(entity.getCreated());
    dto.setCreatedDate(strDate);

    log.info("{} of convertToDTO in {}", WlbConstant.LOGGER_END, HOME);
    return dto;
  }
}
