package com.angusyg.winecellar.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Enum of possibles errors code to send in response of API requests.
 *
 * @since 0.0.1
 */
public enum ExceptionCode {
  INTERNAl_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),

  // Standards http errors
  METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED),
  UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE),
  NOT_ACCEPTABLE(HttpStatus.NOT_ACCEPTABLE),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
  BAD_REQUEST(HttpStatus.BAD_REQUEST),
  NOT_FOUND(HttpStatus.NOT_FOUND),
  SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE),

  // Auth errors
  BAD_CREDENTIALS_USERNAME(HttpStatus.UNAUTHORIZED),
  BAD_CREDENTIALS_PASSWORD(HttpStatus.UNAUTHORIZED),

  // Validation errors
  VALIDATION_ERROR(HttpStatus.BAD_REQUEST),

  // Persistence errors
  DUPLICATE_DATA(HttpStatus.BAD_REQUEST),
  RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND);

  @Getter
  private final HttpStatus value;

  ExceptionCode(HttpStatus value) {
    this.value = value;
  }
}
