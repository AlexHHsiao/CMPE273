package com.example.cpme273.wlb.controller;

import com.example.cpme273.wlb.data.SearchRepository;


import com.example.cpme273.wlb.dto.HomePatchDTO;
import com.example.cpme273.wlb.dto.HomeSearchDTO;
import com.example.cpme273.wlb.service.IHomeService;
import com.example.cpme273.wlb.validator.HomeValidator;

import com.example.cpme273.wlb.dto.EmptyJsonBody;
import com.example.cpme273.wlb.dto.HomeDTO;
import com.example.cpme273.wlb.dto.Role;
import com.example.cpme273.wlb.dto.UserDTO;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping(HomeController.PATH)
@Validated
public class HomeController extends BaseController {

  private static final int VERSION = 1;
  static final String PATH = "/v" + VERSION + "/homes";
  private static final String OFFER_LIST_DIVIDER = "%";

  @Autowired
  IHomeService homeService;
  @Autowired HomeValidator homeValidator;
  @Autowired
  SearchRepository searchRepository;

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<HomeDTO> create(
      @ApiIgnore @RequestHeader HttpHeaders httpHeaders, @Validated @RequestBody HomeDTO homeDTO) {

    log.info("start to create home");
    UserDTO userDTO = restoreSession(httpHeaders);
    homeDTO.setOwnerId(userDTO.getId());

//    check how many home a user have
    int homeCount = homeService.findUserHomeCount(userDTO.getId());

    if(homeCount >= 1 && userDTO.getRole().equals(Role.USER)){
      return ResponseEntity.badRequest().build();
    }

    HomeDTO homeCreateResponse = homeService.create(homeDTO);
    URI location = buildResourceURI(homeCreateResponse.getHomeId().toString());
    log.info("Finish to create home");
    return ResponseEntity.created(location).body(homeCreateResponse);
  }

  @ResponseStatus(HttpStatus.OK)
  @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "{homeId}")
  public ResponseEntity<HomeDTO> patch(
      @ApiIgnore @RequestHeader HttpHeaders httpHeaders,
      @PathVariable("homeId") String homeID,
      @Validated @RequestBody HomePatchDTO homePatchRequest) {

    log.info("Start to Patch home: {}", homePatchRequest);
    UserDTO callerUserDTO = restoreSession(httpHeaders);
    homePatchRequest.setHomeId(UUID.fromString(homeID));
    HomeDTO homePatchResponse = homeService.update(homePatchRequest, callerUserDTO);

    log.info("Finish to patch home");
    return ResponseEntity.ok(homePatchResponse);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "{homeId}")
  public ResponseEntity<HomeDTO> find(
      @ApiIgnore @RequestHeader HttpHeaders httpHeaders, @PathVariable("homeId") String id) {

    log.info("Start to find home");
    restoreSession(httpHeaders);
    HomeDTO homeDTO = homeService.findById(id.toLowerCase());
    log.info("finish to find home");
    return ResponseEntity.ok(homeDTO);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "FindAll API. Append a dash '-' suffix for negation search. For example, ownerId=-xxxxxx means excluding this owner")
  public ResponseEntity<List<HomeDTO>> findAll(
      @ApiIgnore @RequestHeader HttpHeaders httpHeaders,
      @Validated HomeSearchDTO homeSearchDTO,
      HttpServletResponse response) {

    log.info("Start to find all home");
    UserDTO userDTO = restoreSession(httpHeaders);
    List<HomeDTO> homeDTOList = homeService.findAll(homeSearchDTO);
    response.addHeader(
        "total",
        Objects.nonNull(homeSearchDTO.getCount()) ? homeSearchDTO.getCount().toString() : "0");

    Set<HomeDTO> favList = searchRepository.findById(userDTO.getId()).get().getHome_list();
    Set<UUID> homeIdList = new HashSet<>();
    favList.forEach(f -> homeIdList.add(f.getHomeId()));
    for(HomeDTO h : homeDTOList) {
      if (homeIdList.contains(h.getHomeId())) {
        h.setIsFav(true);
      } else {
        h.setIsFav(false);
      }

      String offerList = h.getOfferList();
      if (offerList != null && offerList.length() > 0) {
        Set<String> offerSet = new HashSet<>(Arrays.asList(offerList.split(OFFER_LIST_DIVIDER)));
        if (offerSet.contains(userDTO.getId().toString())) {
          h.setHasApplied(true);
        }
        else{
          h.setHasApplied(false);
        }
      } else {
        h.setHasApplied(false);
      }
    }


    log.info("Finish to find all home");
    return new ResponseEntity(homeDTOList, HttpStatus.OK);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping(value = "{homeId}")
  public ResponseEntity<Void> delete(
      @ApiIgnore @RequestHeader HttpHeaders httpHeaders, @PathVariable("homeId") String homeID) {
    UserDTO userDTO = restoreSession(httpHeaders);
    homeService.delete(homeID, userDTO);

    return ResponseEntity.noContent().build();
  }

  @ResponseStatus(HttpStatus.OK)
  @PostMapping(value = "{homeId}/offer", produces="application/json")
  @ApiOperation(value = "Allow the caller to offer to buy/rent this home")
  public ResponseEntity offer(
      @ApiIgnore @RequestHeader HttpHeaders httpHeaders, @PathVariable("homeId") String homeID) {
      UserDTO userDTO = restoreSession(httpHeaders);
      homeService.sendOffer(homeID, userDTO);
      return ResponseEntity.ok().body(new EmptyJsonBody());
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = "{homeId}/offers/{userId}/accept")
  @ApiOperation(value = "Allow home owner to accept an offer from certain user")
  public ResponseEntity acceptOffer(
          @ApiIgnore @RequestHeader HttpHeaders httpHeaders,
          @PathVariable("homeId") String homeId, @PathVariable("userId") String userId) {
    UserDTO ownerUserDTO = restoreSession(httpHeaders);
    homeService.acceptOffer(homeId, userId, ownerUserDTO);
    return ResponseEntity.ok().body(new EmptyJsonBody());
  }



  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Find all users who offer to buy/rent this home")
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "{homeId}/offers")
  public ResponseEntity<List<UserDTO>> findAllOffers(
          @ApiIgnore @RequestHeader HttpHeaders httpHeaders, @PathVariable("homeId") String homeID,
          HttpServletResponse response) {

    UserDTO userDTO = restoreSession(httpHeaders);
    List<UserDTO> userDtoOfferList = homeService.findAllOffers(homeID, userDTO);
    response.addHeader("total", String.valueOf(userDtoOfferList.size()));
    return new ResponseEntity(userDtoOfferList, HttpStatus.OK);
  }


  private URI buildResourceURI(String id) {
    return URI.create(PATH + "/" + id);
  }

  @InitBinder(value = "homeDTO")
  void initBinderCreateUser(final ServletRequestDataBinder binder) {
    binder.addValidators(homeValidator);
  }
}
