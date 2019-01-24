package com.angusyg.winecellar.core.web.dto;

import com.angusyg.winecellar.core.model.exception.ModelException;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder(alphabetic = true)
public class ErrorApiResponse extends ApiResponse {
  private String code;
  private String message;

  public ErrorApiResponse(String code) {
    this.code = code;
  }

  public ErrorApiResponse(String code, String message, Object... data) {
    this.code = code;
    this.message = message;
    this.data = data.length > 0 ? data : null;
  }

  public ErrorApiResponse(ModelException ex, Object... data) {
    this.code = ex.getCode();
    this.message = ex.getMessage();
    this.data = data.length > 0 ? data : null;
  }
}
