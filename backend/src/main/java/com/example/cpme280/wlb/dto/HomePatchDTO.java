package com.example.cpme273.wlb.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@ApiModel("HomePatchDTO")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HomePatchDTO {
    @ApiModelProperty private UUID homeId;
    @ApiModelProperty private UUID ownerId;
    @ApiModelProperty private HomeType homeType;
    private Boolean available;
    private String offerType;
    private String zip;
    private Double price;
    private String streetNumber;
    private String street1;
    private String street2; // apt number
    private String city;
    private String state;
    private Integer numOfBedroom;
    private Integer numOfBathroom;
    private Boolean hasParking;
    private Double sqft;
    private String description;
    private String openHour;
    private String offerList;
    private List<String> imageUrlList;
}
