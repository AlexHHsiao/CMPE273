package com.example.cpme273.wlb.data;

import com.example.cpme273.wlb.data.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SearchRepository extends JpaRepository<Search, UUID> {

}
