package com.example.cpme273.wlb.controller;

import com.example.cpme273.wlb.data.SearchRepository;
import com.example.cpme273.wlb.data.entity.Search;
import com.example.cpme273.wlb.dto.HomeDTO;
import com.example.cpme273.wlb.dto.UserDTO;
import com.example.cpme273.wlb.service.IHomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.net.URI;
import java.util.*;

@Slf4j
@RestController
@Validated
@RequestMapping(FavoriteController.PATH)
public class FavoriteController extends BaseController {
    private static final int VERSION = 1;
    static final String PATH = "/v" + VERSION + "/fav";
    @Autowired
    IHomeService homeService;
    @Autowired
    SearchRepository searchRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "{id}")
    public ResponseEntity<Search> create(
            @ApiIgnore @RequestHeader HttpHeaders httpHeaders, @PathVariable("id") String id) {

        UserDTO userDTO = restoreSession(httpHeaders);
        Optional<Search> entity = searchRepository.findById(userDTO.getId());
        HomeDTO homeDTO = homeService.findById(id);
        Set<HomeDTO> list;
        Search save;
        boolean add;
        list = entity.get().getHome_list();
        add = list.add(homeDTO);
        save = entity.get().setHome_list(list);

        searchRepository.saveAndFlush(save);
        URI location = buildResourceURI(userDTO.getId().toString());
        return add ? ResponseEntity.created(location).body(save) : ResponseEntity.badRequest().build();
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "{id}")
    public ResponseEntity<Void> delete (
            @ApiIgnore @RequestHeader HttpHeaders httpHeaders, @PathVariable("id") String id
    ) {
        UserDTO userDTO = restoreSession(httpHeaders);
        HomeDTO homeDTO = homeService.findById(id);
        Optional<Search> entity = searchRepository.findById(userDTO.getId());
        Set<HomeDTO> list = entity.get().getHome_list();
        Iterator<HomeDTO> it = list.iterator();
        boolean remove = false;
        while(it.hasNext()) {
            HomeDTO home = it.next();
            if(home.getHomeId().toString().equals(id)) {
                remove = true;
                it.remove();
            }
        }
        log.info(list.toString());
        Search save = entity.get().setHome_list(list);
        searchRepository.saveAndFlush(save);

        return remove ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public ResponseEntity<Set<HomeDTO>> findAllFav(
            @ApiIgnore @RequestHeader HttpHeaders httpHeaders) {
        UserDTO userDTO = restoreSession(httpHeaders);
        Optional<Search> entity = searchRepository.findById(userDTO.getId());
        Set<HomeDTO> result = entity.get().getHome_list();
        for(HomeDTO home : result) {
            home.setIsFav(true);
            UserDTO homeOwnerDTO = userService.findById(home.getOwnerId().toString());
            home.setOwner(homeOwnerDTO);
        }
        return entity.isPresent() ? ResponseEntity.ok().body(result) : ResponseEntity.notFound().build();
    }



    private URI buildResourceURI(String id) {
        return URI.create(PATH + "/" + id);
    }
}
