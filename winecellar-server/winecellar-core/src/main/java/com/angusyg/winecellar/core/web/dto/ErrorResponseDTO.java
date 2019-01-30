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
public class ErrorResponseDTO extends ResponseDTO {
  // Default error code for uncaught exceptions
  private static final String INTERNAL_ERROR_CODE = "INTERNAL_ERROR";

  // Error code
  private String code;

  // Error message
  private String message;

  /**
   * Creates an {@link ErrorResponseDTO} instance with default error code.
   */
  public ErrorResponseDTO() {
    this.code = INTERNAL_ERROR_CODE;
  }

  /**
   * Creates an {@link ErrorResponseDTO} instance from {@link HttpStatus}.
   *
   * @param status {@link HttpStatus to extract code from.
   */
  public ErrorResponseDTO(HttpStatus status) {
    this.code = status.name();
  }

  /**
   * Creates an {@link ErrorResponseDTO} instance from {@link HttpStatus} and message.
   *
   * @param status  {@link HttpStatus to extract code from.
   * @param message message of error
   */
  public ErrorResponseDTO(HttpStatus status, String message) {
    this.code = status.name();
    this.message = message;
  }

  /**
   * Creates an {@link ErrorResponseDTO} instance from {@link HttpStatus}, message and data.
   *
   * @param status  {@link HttpStatus to extract code from.
   * @param message message of error
   * @param data    data to add
   */
  public ErrorResponseDTO(HttpStatus status, String message, Object data) {
    this.code = status.name();
    this.message = message;
    this.data = data;
  }

  /**
   * Creates an {@link ErrorResponseDTO} instance with specified error code.
   *
   * @param code error code
   */
  public ErrorResponseDTO(String code) {
    this.code = code;
  }

  /**
   * Creates an {@link ErrorResponseDTO} instance with specified error code
   * and message.
   *
   * @param code    error code
   * @param message message of error
   */
  public ErrorResponseDTO(String code, String message) {
    this.code = code;
    this.message = message;
  }

  /**
   * Creates an {@link ErrorResponseDTO} instance with specified error code,
   * message and data.
   *
   * @param code    error code
   * @param message message of error
   * @param data    data to add
   */
  public ErrorResponseDTO(String code, String message, Object data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  /**
   * Creates an {@link ErrorResponseDTO} instance from a given exception.
   * If exception is an instance of {@link ApiException}, exception code
   * is used for error code, otherwise default code is used.
   *
   * @param ex   exception to analyze
   */
  public ErrorResponseDTO(Exception ex) {
    // Checks if ex is instance of ApiException to use its code
    if (ex instanceof ApiException) {
      this.code = ((ApiException) ex).getCode();
      this.message = ex.getMessage();
    } else {
      this.code = INTERNAL_ERROR_CODE;
    }
  }

  /**
   * Creates an {@link ErrorResponseDTO} instance from a given exception and data.
   * If exception is an instance of {@link ApiException}, exception code
   * is used for error code, otherwise default code is used.
   *
   * @param ex   exception to analyze
   * @param data data to add
   */
  public ErrorResponseDTO(Exception ex, Object data) {
    // Checks if ex is instance of ApiException to use its code
    if (ex instanceof ApiException) {
      this.code = ((ApiException) ex).getCode();
      this.message = ex.getMessage();
    } else {
      this.code = INTERNAL_ERROR_CODE;
    }
    this.data = data;
  }
}
