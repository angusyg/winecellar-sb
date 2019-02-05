package com.angusyg.winecellar.core.security.jwt;

import lombok.Data;

/**
 * JWT Token payload.
 *
 * @since 0.0.1
 */
@Data
public class JwtTokenPayload {
  private String id;
  private String username;
  private String roles;
}
