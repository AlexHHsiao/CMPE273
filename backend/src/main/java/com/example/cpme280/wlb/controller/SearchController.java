package com.example.cpme273.wlb.controller;

import com.example.cpme273.wlb.data.SearchRepository;
import com.example.cpme273.wlb.data.entity.Search;
import com.example.cpme273.wlb.dto.HomeSearchDTO;
import com.example.cpme273.wlb.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.net.URI;
import java.util.*;

@Slf4j
@RestController
@RequestMapping(SearchController.PATH)
@Validated
public class SearchController extends BaseController{
    private static final int VERSION = 1;
    static final String PATH = "/v" + VERSION + "/search";
    @Autowired
    SearchRepository searchRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public ResponseEntity<Search> create(
            @ApiIgnore @RequestHeader HttpHeaders httpHeaders, @Validated @RequestBody HomeSearchDTO homeSearchDTO) {
        UserDTO userDTO = restoreSession(httpHeaders);
        Optional<Search> entity = searchRepository.findById(userDTO.getId());
        Set<HomeSearchDTO> list = entity.get().getSearch_list();
        boolean add = list.add(homeSearchDTO);
        Search save = entity.get().setSearch_list(list);

        searchRepository.saveAndFlush(save);
        return add ? ResponseEntity.ok(save) : ResponseEntity.badRequest().build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping()
    public ResponseEntity<Void> delete (
            @ApiIgnore @RequestHeader HttpHeaders httpHeaders, @Validated HomeSearchDTO homeSearchDTO
    ) {
        UserDTO userDTO = restoreSession(httpHeaders);
        Optional<Search> entity = searchRepository.findById(userDTO.getId());
        Set<HomeSearchDTO> list = entity.get().getSearch_list();

        boolean remove = list.remove(homeSearchDTO);
        log.info(list.toString());
        Search save = entity.get().setSearch_list(list);
        searchRepository.saveAndFlush(save);

        return remove ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public ResponseEntity<Set<HomeSearchDTO>> findAllFav(
            @ApiIgnore @RequestHeader HttpHeaders httpHeaders) {
        UserDTO userDTO = restoreSession(httpHeaders);
        Optional<Search> entity = searchRepository.findById(userDTO.getId());

        return entity.isPresent() ? ResponseEntity.ok(entity.get().getSearch_list()) : ResponseEntity.notFound().build();
    }

    private URI buildResourceURI(String id) {
        return URI.create(PATH + "/" + id);
    }
}
