package com.angusyg.winecellar.model.user.dto;

import com.angusyg.winecellar.model.user.config.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * User DTO
 *
 * @since 0.0.1
 */
@Data
@NoArgsConstructor
public class UserDto {
  // User id
  private Long id;
  // User name
  private String username;
  // User email address
  private String email;
  // User list of roles
  private Set<Role> roles;
}
