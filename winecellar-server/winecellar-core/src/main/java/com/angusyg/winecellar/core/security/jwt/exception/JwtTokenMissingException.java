package com.angusyg.winecellar.core.security.jwt.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown when no JWT was found in request headers.
 *
 * @since 0.0.1
 */
public class JwtTokenMissingException extends AuthenticationException {
  /**
   * Creates an instance of {@link JwtTokenMissingException} with specified message.
   *
   * @param message message of exception
   */
  public JwtTokenMissingException(String message) {
    super(message);
  }
}
