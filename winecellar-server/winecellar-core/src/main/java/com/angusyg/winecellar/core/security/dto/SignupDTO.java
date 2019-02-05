package com.angusyg.winecellar.core.security.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * Signup informations.
 *
 * @since 0.0.1
 */
@Data
public class SignupDTO {
  // User name
  @NotEmpty
  private String username;

  // User password
  @NotEmpty
  private String password;

  // User email address
  @NotEmpty
  @Email
  private String email;
}
