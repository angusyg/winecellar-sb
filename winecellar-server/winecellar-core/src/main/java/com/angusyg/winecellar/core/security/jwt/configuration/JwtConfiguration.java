package com.angusyg.winecellar.core.security.jwt.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT Token security configuration
 *
 * @since 0.0.1
 */
@Data
@Component
@ConfigurationProperties("security.jwt")
public class JwtConfiguration {
  // Authentication URI to not protect
  private String uri;
  // Authentication header name containing JWT Token
  private String header;
  // Authentication header scheme value
  private String prefix;
  // JWT Token expiration time
  private int expiration;
  // JWT Token generation secret
  private String secret;
}

