package com.angusyg.winecellar.core.model.exception;

/**
 * Thrown when no resource element is found.
 *
 * @since 0.0.1
 */
public class NoResourceModelException extends ModelException {
  // Default error code
  private static final String ERROR_CODE = "NO_RESOURCE_FOUND";

  /**
   * Creates a {@link NoResourceModelException} with default code and no message.
   */
  public NoResourceModelException() {
    super(ERROR_CODE);
  }

  /**
   * Creates a {@link NoResourceModelException} with default code and a detail message.
   *
   * @param message detail message
   */
  public NoResourceModelException(String message) {
    super(ERROR_CODE, message);
  }
}
