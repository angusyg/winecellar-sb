package com.angusyg.winecellar.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Login DTO.
 *
 * @since 0.0.1
 */
@Data
public class LoginDTO {
  // User login username
  @NotNull
  private String username;

  // User login password
  @NotNull
  private String password;
}
