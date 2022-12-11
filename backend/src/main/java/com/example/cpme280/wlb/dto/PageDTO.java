package com.example.cpme273.wlb.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class PageDTO implements Serializable {

  @ApiModelProperty(notes = "Query start index")
  private String start;

  @ApiModelProperty(notes = "Total number of transformation to fetch")
  private String limit;

  @ApiModelProperty(notes = "Sort attributes")
  private String sort;

  @JsonIgnore
  @ApiModelProperty(hidden = true)
  private Long count;
}
