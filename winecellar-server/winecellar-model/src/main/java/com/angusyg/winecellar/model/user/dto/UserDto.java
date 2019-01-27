package com.angusyg.winecellar.model.user.dto;

import com.angusyg.winecellar.model.user.entity.Role;
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
