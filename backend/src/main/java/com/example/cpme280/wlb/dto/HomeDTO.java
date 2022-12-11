package com.example.cpme273.wlb.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@ApiModel("HomeDTO")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HomeDTO {
    private UUID homeId;
    private UUID ownerId;
    private Boolean available;
    @ApiModelProperty(hidden = true) private UserDTO owner;
    @ApiModelProperty @NotNull private HomeType homeType;
    @ApiModelProperty @NotNull private OfferType offerType;
    @NotEmpty private String zip;
    @NotNull private Double price;
    @NotEmpty private String streetNumber;
    @NotEmpty private String street1;
    private String street2; // apt number
    @NotEmpty private String city;
    @NotEmpty private String state;
    @NotNull private Integer numOfBedroom;
    @NotNull private Integer numOfBathroom;
    @NotNull private Boolean hasParking;
    @NotNull private Double sqft;
    @NotNull private List<String> imageUrlList;
    private Boolean isFav;
    private Boolean hasApplied;
    @ApiModelProperty(hidden = true) private String offerList;
    @NotEmpty private String description;
    private String openHour;
    private String createdDate;
}
