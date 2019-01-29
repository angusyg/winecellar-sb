package com.angusyg.winecellar.core.web.dto;

import com.angusyg.winecellar.core.web.exception.ApiException;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * API error response to be serialized as JSON
 *
 * @since 0.0.1
 */
@Data
public class ErrorApiResponse extends ApiResponse {
  // Default error code for uncaught exceptions
  private static final String INTERNAL_ERROR_CODE = "INTERNAL_ERROR";

  // Error code
  private String code;

  // Error message
  private String message;

  /**
   * Creates an {@link ErrorApiResponse} instance with default error code.
   */
  public ErrorApiResponse() {
    this.code = INTERNAL_ERROR_CODE;
  }

  /**
   * Creates an {@link ErrorApiResponse} instance from {@link HttpStatus}.
   *
   * @param status {@link HttpStatus to extract code from.
   */
  public ErrorApiResponse(HttpStatus status) {
    this.code = status.name();
  }

  /**
   * Creates an {@link ErrorApiResponse} instance from {@link HttpStatus}, message and data.
   *
   * @param status  {@link HttpStatus to extract code from.
   * @param message message of error
   * @param data    optional data
   */
  public ErrorApiResponse(HttpStatus status, String message, Object... data) {
    this.code = status.name();
    this.message = message;
    this.data = data.length > 0 ? data : null;
  }

  /**
   * Creates an {@link ErrorApiResponse} instance with specified error code.
   *
   * @param code error code
   */
  public ErrorApiResponse(String code) {
    this.code = code;
  }

  /**
   * Creates an {@link ErrorApiResponse} instance with specified error code,
   * message and data.
   *
   * @param code    error code
   * @param message message of error
   * @param data    optional data
   */
  public ErrorApiResponse(String code, String message, Object... data) {
    this.code = code;
    this.message = message;
    this.data = data.length > 0 ? data : null;
  }

  /**
   * Creates an {@link ErrorApiResponse} instance from a given exception and data.
   * If exception is an instance of {@link ApiException}, exception code
   * is used for error code, otherwise default code is used.
   *
   * @param ex   exception to analyze
   * @param data optional data
   */
  public ErrorApiResponse(Exception ex, Object... data) {
    // Checks if ex is instance of ApiException to use its code
    if (ex instanceof ApiException) {
      this.code = ((ApiException) ex).getCode();
    } else {
      this.code = INTERNAL_ERROR_CODE;
    }
    this.data = data.length > 0 ? data : null;
  }

  /**
   * Creates an {@link ErrorApiResponse} instance from a given exception message and data.
   * If exception is an instance of {@link ApiException}, exception code
   * is used for error code, otherwise default code is used.
   *
   * @param ex      exception to analyze
   * @param message message of error
   * @param data    optional data
   */
  public ErrorApiResponse(String message, Exception ex, Object... data) {
    if (ex instanceof ApiException) {
      this.code = ((ApiException) ex).getCode();
    } else {
      this.code = INTERNAL_ERROR_CODE;
    }
    this.message = message;
    this.data = data.length > 0 ? data : null;
  }
}
