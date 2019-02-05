package com.angusyg.winecellar.core.security.exception;

import com.angusyg.winecellar.core.exception.ApiException;
import com.angusyg.winecellar.core.exception.ExceptionCode;

/**
 * Exception thrown when authentication failed due to bad username.
 *
 * @since 0.0.1
 */
public class BadCredentialsUsernameException extends ApiException {
  /**
   * Creates a {@link BadCredentialsUsernameException} with no message.
   */
  public BadCredentialsUsernameException() {
    super(ExceptionCode.BAD_CREDENTIALS_USERNAME);
  }

  /**
   * Creates a {@link BadCredentialsUsernameException} with a detail message.
   *
   * @param message detail message
   */
  public BadCredentialsUsernameException(String message) {
    super(ExceptionCode.BAD_CREDENTIALS_USERNAME, message);
  }
}
