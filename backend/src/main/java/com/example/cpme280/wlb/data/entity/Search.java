package com.example.cpme273.wlb.data.entity;

import com.example.cpme273.wlb.dto.HomeDTO;
import com.example.cpme273.wlb.dto.HomeSearchDTO;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.*;

@Entity(name = "Search")
@Table(name = "search", schema = "wlb")
@Data
@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(
        name = "jsonb",
        typeClass = JsonBinaryType.class
)
public class Search implements Serializable {

    @Id
    @Column(name = "user_id", unique = true)
    private UUID userId;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Set<HomeSearchDTO> search_list;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Set<HomeDTO> home_list;

    public Set<HomeSearchDTO> getSearch_list() {
        return search_list;
    }

    public Search setSearch_list(Set<HomeSearchDTO> search_list) {
        this.search_list = search_list;
        return this;
    }

    public Set<HomeDTO> getHome_list() {
        return home_list;
    }

    public Search setHome_list(Set<HomeDTO> home_list) {
        this.home_list = home_list;
        return this;
    }
}
