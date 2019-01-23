package com.angusyg.winecellar.core.web.dto;

import com.angusyg.winecellar.core.web.utils.WebUtils;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Data
@JsonPropertyOrder(alphabetic = true)
public class ErrorApiResponse extends ApiResponse {
  private String code;
  private String message;

  public ErrorApiResponse(String code) {
    this.code = code;
  }

  public ErrorApiResponse(String code, Object data) {
    this.code = code;
    if (data instanceof String) {
      this.message = (String) data;
    }
    if (data instanceof HttpServletRequest) {
      this.data = WebUtils.buildRequestData((HttpServletRequest) data);
    } else {
      this.data = data;
    }
  }

  public ErrorApiResponse(String code, String message, Object data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }
}
