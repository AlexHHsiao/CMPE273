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
@ApiModel("User search dto")
public class UserSearchDTO extends PageDTO {

  @ApiModelProperty(notes = "isActive attributes")
  private Boolean isActive;

  @ApiModelProperty(notes = "role attributes")
  private String role;

  @JsonIgnore
  @ApiModelProperty(hidden = true)
  private Map<String, Object> whereClause;

  @JsonIgnore
  @ApiModelProperty(hidden = true)
  private List<String> orderByClause;
}
