package com.angusyg.winecellar.core.model.exception;

import lombok.Getter;

/**
 * Thrown when an exception occurs during model operations.
 *
 * @since 0.0.1
 */
public class ModelException extends Exception {
  // Default error code of exception
  @Getter
  private String code;

  /**
   * Creates a {@link ModelException} with a code and no message.
   *
   * @param code model exception code
   */
  public ModelException(String code) {
    super();
    this.code = code;
  }

  /**
   * Creates a {@link ModelException} with a code and a detail message.
   *
   * @param code    model exception code
   * @param message detail message
   */
  public ModelException(String code, String message) {
    super(message);
    this.code = code;
  }
}
