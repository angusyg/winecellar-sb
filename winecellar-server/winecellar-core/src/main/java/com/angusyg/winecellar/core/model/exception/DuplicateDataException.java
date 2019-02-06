package com.angusyg.winecellar.core.model.exception;

import com.angusyg.winecellar.core.exception.ApiException;
import com.angusyg.winecellar.core.exception.ExceptionCode;

/**
 * Exception thrown when data is duplicated in repository.
 *
 * @since 0.0.1
 */
public class DuplicateDataException extends ApiException {
  /**
   * Creates a {@link DuplicateDataException} with no message.
   */
  public DuplicateDataException() {
    super(ExceptionCode.DUPLICATE_DATA);
  }

  /**
   * Creates a {@link DuplicateDataException} with a detail message.
   *
   * @param message detail message
   */
  public DuplicateDataException(String message) {
    super(ExceptionCode.DUPLICATE_DATA, message);
  }
}
