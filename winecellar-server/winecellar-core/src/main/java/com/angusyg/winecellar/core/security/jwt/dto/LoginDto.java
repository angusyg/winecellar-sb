package com.angusyg.winecellar.core.security.jwt.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginDto {
  // User login username
  @NotNull
  private String username;

  // User login password
  @NotNull
  private String password;
}
