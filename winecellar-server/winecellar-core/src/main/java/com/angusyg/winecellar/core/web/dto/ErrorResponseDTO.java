package com.angusyg.winecellar.core.web.dto;

import com.angusyg.winecellar.core.exception.ApiException;
import com.angusyg.winecellar.core.exception.ExceptionCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * API error response to be serialized as JSON
 *
 * @since 0.0.1
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDTO {
  // Response data
  private Object data;

  // Error code
  private ExceptionCode code;

  // Error message
  private String message;

  /**
   * Creates an {@link ErrorResponseDTO} instance with default error code.
   */
  public ErrorResponseDTO() {
    this.code = ExceptionCode.INTERNAL_ERROR;
  }

  /**
   * Creates an {@link ErrorResponseDTO} instance from {@link ExceptionCode}.
   *
   * @param code {@link ExceptionCode} error code.
   */
  public ErrorResponseDTO(ExceptionCode code) {
    this.code = code;
  }

  /**
   * Creates an {@link ErrorResponseDTO} instance from {@link ExceptionCode} and message.
   *
   * @param code    {@link ExceptionCode} error code.
   * @param message message of error
   */
  public ErrorResponseDTO(ExceptionCode code, String message) {
    this(code);
    this.message = message;
  }

  /**
   * Creates an {@link ErrorResponseDTO} instance from {@link ExceptionCode}, message and data.
   *
   * @param code    {@link ExceptionCode} error code.
   * @param message message of error
   * @param data    data to add
   */
  public ErrorResponseDTO(ExceptionCode code, String message, Object data) {
    this(code, message);
    this.data = data;
  }

  /**
   * Creates an {@link ErrorResponseDTO} instance from a given exception.
   * If exception is an instance of {@link ApiException}, exception code
   * is used for error code, otherwise default code is used.
   *
   * @param ex exception to analyze
   */
  public ErrorResponseDTO(Exception ex) {
    // Checks if ex is instance of ApiException to use its code
    if (ex instanceof ApiException) {
      this.code = ((ApiException) ex).getCode();
      this.message = ex.getMessage();
    } else {
      this.code = ExceptionCode.INTERNAL_ERROR;
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
    this(ex);
    this.data = data;
  }
}
