package com.example.cpme273.wlb.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("home search dto")
public class HomeSearchDTO extends PageDTO {

  private String ownerId;
  private String homeType;
  private String offerType;
  private String zip;
  private Double minPrice;
  private Double maxPrice;
  private String streetNumber;
  private String street1;
  private String street2; // apt number
  private String city;
  private String state;
  private Integer numOfBedroom;
  private Integer numOfBathroom;
  private Boolean hasParking;
  private Double sqft;
  private String name;

  @JsonIgnore
  @ApiModelProperty(hidden = true)
  private Map<String, Object> whereClause;

  @JsonIgnore
  @ApiModelProperty(hidden = true)
  private List<String> orderByClause;
}
