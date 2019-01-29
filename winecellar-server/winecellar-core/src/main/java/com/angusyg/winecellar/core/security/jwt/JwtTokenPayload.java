package com.angusyg.winecellar.core.security.jwt;

import lombok.Data;

@Data
public class JwtTokenPayload {
  private String id;
  private String username;
  private String roles;
}
