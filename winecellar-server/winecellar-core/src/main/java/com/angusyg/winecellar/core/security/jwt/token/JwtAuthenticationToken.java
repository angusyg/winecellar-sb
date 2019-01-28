package com.angusyg.winecellar.core.security.jwt.token;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;

/**
 * JWT authentication token.
 *
 * @since 0.0.1
 */
@Data
public class JwtAuthenticationToken {
  // User name
  private String username;

  // User roles
  private String roles;

  /**
   * Creates an instance of {@link JwtAuthenticationToken} with a specified {@link UserDetails}
   *
   * @param userDetails user details
   */
  public JwtAuthenticationToken(UserDetails userDetails) {
    this.username = userDetails.getUsername();
    this.roles = StringUtils.collectionToCommaDelimitedString(
            userDetails
                    .getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList())
    );
  }
}
