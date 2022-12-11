package com.example.cpme273.wlb.data;

import com.example.cpme273.wlb.data.entity.Home;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeRepository extends JpaRepository<Home, UUID> {

  Page<Home> findAll(Specification<Home> homeSpecification, Pageable homePageRequest);
  List<Home> findAllByOwnerId(UUID id);
}
