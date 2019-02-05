package com.angusyg.winecellar.core.security.exception;

import com.angusyg.winecellar.core.exception.ApiException;
import com.angusyg.winecellar.core.exception.ExceptionCode;

/**
 * Exception thrown when authentication failed due to bad password.
 *
 * @since 0.0.1
 */
public class BadCredentialsPasswordException extends ApiException {
  /**
   * Creates a {@link BadCredentialsPasswordException} with no message.
   */
  public BadCredentialsPasswordException() {
    super(ExceptionCode.BAD_CREDENTIALS_PASSWORD);
  }

  /**
   * Creates a {@link BadCredentialsPasswordException} with a detail message.
   *
   * @param message detail message
   */
  public BadCredentialsPasswordException(String message) {
    super(ExceptionCode.BAD_CREDENTIALS_PASSWORD, message);
  }
}
