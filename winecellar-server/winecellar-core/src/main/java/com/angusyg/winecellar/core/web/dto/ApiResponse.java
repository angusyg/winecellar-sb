package com.angusyg.winecellar.core.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * REST API response to be serialized as JSON
 *
 * @since 0.0.1
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
  // Response data
  protected Object data;

  /**
   * Creates an {@link ApiResponse} instance with no data
   */
  public ApiResponse() {
    this.data = null;
  }

  /**
   * Creates {@link ApiResponse} instance with specified data
   *
   * @param data data to send in JSON response
   */
  public ApiResponse(Object data) {
    this.data = data;
  }
}
