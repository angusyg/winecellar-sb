package com.angusyg.winecellar.core.web.dto;

import lombok.Data;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

/**
 * Validation errors DTO to be sent in API response.
 *
 * @since 0.0.1
 */
@Data
public class ValidationErrorDTO {
  // Error on fields
  private List<FieldErrorDto> fieldErrors;

  /**
   * Creates an instance of {@link ValidationErrorDTO} from
   * fields validation errors.
   *
   * @param fieldErrors fields validation errors.
   */
  public ValidationErrorDTO(List<FieldError> fieldErrors) {
    this.fieldErrors = new ArrayList<>();
    for (FieldError fieldError : fieldErrors) {
      this.fieldErrors.add(new FieldErrorDto(fieldError));
    }
  }

  /**
   * Field validation error DTO.
   *
   * @since 0.0.1
   */
  @Data
  class FieldErrorDto {
    // Field name of error
    private String field;
    // Constraint name of error
    private String constraint;
    // Error message
    private String message;

    /**
     * Creates and instance of {@link FieldErrorDto} from a
     * field validation error.
     *
     * @param fieldError
     */
    public FieldErrorDto(FieldError fieldError) {
      this.field = fieldError.getField();
      this.constraint = fieldError.getCode();
      this.message = fieldError.getDefaultMessage();
    }
  }
}
