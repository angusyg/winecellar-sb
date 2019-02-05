package com.angusyg.winecellar.core.security.jwt.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown when JWT Token was not found in request headers.
 *
 * @since 0.0.1
 */
public class MissingJwtTokenException extends AuthenticationException {
  /**
   * Creates an instance of {@link MissingJwtTokenException} with specified message.
   *
   * @param message message of exception
   */
  public MissingJwtTokenException(String message) {
    super(message);
  }
}
