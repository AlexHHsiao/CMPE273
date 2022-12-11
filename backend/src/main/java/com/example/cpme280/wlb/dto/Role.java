package com.example.cpme273.wlb.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum Role {
  ADMIN("admin"),
  USER("user"),
  REALTOR("realtor");
  @Getter @JsonValue private final String value;

  Role(String value) {
    this.value = value;
  }
}
