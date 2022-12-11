package com.example.cpme273.wlb.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import lombok.Getter;

@ApiModel
public enum HomeType {
  APARTMENT,
  TOWN_HOUSE,
  SINGLE_HOUSE
}
