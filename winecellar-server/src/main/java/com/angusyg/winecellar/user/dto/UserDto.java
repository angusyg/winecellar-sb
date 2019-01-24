package com.angusyg.winecellar.user.dto;

import com.angusyg.winecellar.user.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class UserDto {
  private Long id;
  private String username;
  private String email;
  private Set<Role> roles;
}
