package com.angusyg.winecellar.core.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Authentication token sent an authentication success.
 *
 * @since 0.0.1
 */
@Data
@AllArgsConstructor
public class TokenDTO {
  // JWT token as string
  private String authToken;
}
