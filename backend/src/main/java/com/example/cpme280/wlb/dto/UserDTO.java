package com.example.cpme273.wlb.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;

import java.util.UUID;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel("UserDTO")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class UserDTO {

  private UUID id;
  @NotEmpty private String username;
  @NotEmpty private String password;
  @NotNull @Email private String email;
  @NotEmpty private String role;
  private Boolean isActive;
  @NotEmpty private String legalName;
  @NotEmpty private String phone;
  @NotEmpty private String birthday;
  @NotEmpty private String ssn;
  @NotNull private Integer creditScore;
}
