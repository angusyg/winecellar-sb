package com.angusyg.winecellar.core.security.jwt.dto;

import lombok.Data;

/**
 * JWT Token payload.
 *
 * @since 0.0.1
 */
@Data
public class JwtTokenPayloadDTO {
  private String id;
  private String username;
  private String authorities;
}
