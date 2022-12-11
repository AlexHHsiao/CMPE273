package com.example.cpme273.wlb.data.entity;

import com.example.cpme273.wlb.dto.HomeType;
import com.example.cpme273.wlb.dto.OfferType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "home", schema = "wlb")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Home implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "home_id", unique = true)
    private UUID homeId;

    @Column(name = "owner_id")
    private UUID ownerId;

    @Column(name = "home_type")
    @Enumerated(EnumType.STRING)
    private HomeType homeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "offer_type")
    private OfferType offerType;

    @Column(name = "available")
    private boolean available;

    @Column(name = "zip")
    private String zip;

    @Column(name = "price")
    private double price;

    @Column(name = "street_number")
    private String streetNumber;

    @Column(name = "street1")
    private String street1;

    @Column(name = "street2", nullable = true)
    private String street2; // apt number

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "num_bedroom")
    private int numOfBedroom;

    @Column(name = "num_bathroom")
    private int numOfBathroom;

    @Column(name = "has_parking")
    private boolean hasParking;

    @Column(name = "sqft")
    private double sqft;

    @Column(name = "description")
    private String description;

    @Column(name = "open_hour")
    private String openHour;

    @Column(name = "offer_list")
    private String offerList;

    @Column(name = "created", nullable = false, updatable = false)
    private Date created;

    @Column(name = "updated")
    private Date updated;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<String> imageUrlList;

    @PreUpdate
    public void onPreUpdate() {
        setUpdated(new Date());
    }

    @PrePersist
    public void onPrePersist() {
        if (Objects.isNull(homeId)) {
            homeId = UUID.randomUUID();
            available = true;
        }
        setCreated(new Date());
    }
}
