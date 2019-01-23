package com.angusyg.winecellar.core.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
  protected Object data;

  public ApiResponse() {
    this.data = null;
  }

  public ApiResponse(Object data) {
    this.data = data;
  }
}
