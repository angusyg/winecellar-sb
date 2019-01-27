package com.angusyg.winecellar.core.web.exception;

import lombok.Getter;

/**
 * Thrown when an exception occurs during API operations.
 *
 * @since 0.0.1
 */
public class ApiException extends Exception {
  // Default error code of exception
  @Getter
  private String code;

  /**
   * Creates a {@link ApiException} with a code and no message.
   *
   * @param code model exception code
   */
  public ApiException(String code) {
    super();
    this.code = code;
  }

  /**
   * Creates a {@link ApiException} with a code and a detail message.
   *
   * @param code    model exception code
   * @param message detail message
   */
  public ApiException(String code, String message) {
    super(message);
    this.code = code;
  }

  /**
   * Creates a {@link ApiException} with a code, a detail message and exception.
   *
   * @param code      model exception code
   * @param message   detail message
   * @param exception associated exception
   */
  public ApiException(String code, String message, Throwable exception) {
    super(message, exception);
    this.code = code;
  }
}
