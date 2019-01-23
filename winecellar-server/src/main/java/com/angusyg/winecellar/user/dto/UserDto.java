package com.angusyg.winecellar.user.dto;

import com.angusyg.winecellar.user.entity.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
  private Long id;
  private String username;
  private String email;
  private Set<Role> roles;
}
