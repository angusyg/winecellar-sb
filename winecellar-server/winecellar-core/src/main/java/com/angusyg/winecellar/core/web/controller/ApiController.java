package com.angusyg.winecellar.core.web.controller;

import com.angusyg.winecellar.core.web.dto.ErrorApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * API base controller
 * Handles uncaught exceptions to return API errors
 *
 * @since 0.0.1
 */
@Slf4j
public class ApiController {
  /**
   * Handles uncaught exceptions to return a standard API error
   *
   * @param req current request
   * @param ex  uncaught exception
   * @return an API error
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorApiResponse handleException(HttpServletRequest req, Exception ex) {
    log.error("Sending API error for uncaught exception: {}", ex.getMessage());
    return new ErrorApiResponse();
  }
}
