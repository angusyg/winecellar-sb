package com.angusyg.winecellar.core.web.controller;

import com.angusyg.winecellar.core.web.dto.ErrorApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class ApiController {
  private static final String INTERNAL_ERROR_CODE = "INTERNAL_ERROR";

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorApiResponse handleException(HttpServletRequest req, Exception ex) {
    log.error(ex.getMessage());
    return new ErrorApiResponse(INTERNAL_ERROR_CODE, ex.getMessage());
  }
}
