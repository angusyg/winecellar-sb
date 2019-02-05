package com.angusyg.winecellar.core.security.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Login DTO.
 *
 * @since 0.0.1
 */
@Data
public class LoginDTO {
  // User login username
  @NotEmpty
  private String username;

  // User login password
  @NotEmpty
  private String password;
}
