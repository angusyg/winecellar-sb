package com.angusyg.winecellar.auth.exception;

import com.angusyg.winecellar.core.web.exception.ApiException;

/**
 * Exception thrown when authentication failed
 * for bad credentials (bad username or password)
 *
 * @since 0.0.1
 */
public class BadCredentialsException extends ApiException {
  /**
   * @see ApiException#ApiException(String)
   */
  public BadCredentialsException(String code) {
    super(code);
  }

  /**
   * @see ApiException#ApiException(String)
   */
  public BadCredentialsException(String code, String message) {
    super(code, message);
  }

  /**
   * @see ApiException#ApiException(String)
   */
  public BadCredentialsException(String code, String message, Throwable exception) {
    super(code, message, exception);
  }
}
