package com.angusyg.winecellar.auth.controller;

import com.angusyg.winecellar.auth.dto.LoginDTO;
import com.angusyg.winecellar.auth.exception.BadCredentialsException;
import com.angusyg.winecellar.auth.service.AuthService;
import com.angusyg.winecellar.core.web.controller.BaseController;
import com.angusyg.winecellar.core.web.dto.ErrorResponseDTO;
import com.angusyg.winecellar.core.web.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Authentication controller.
 * Handles login and sign up.
 *
 * @since 0.0.1
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {
  @Autowired
  private AuthService authService;

  /**
   * Login endpoint.
   *
   * @param loginDto login infos (username and password)
   * @return an API response with JWT Token if ok
   */
  @PostMapping("/login")
  @Transactional(readOnly = true)
  public ResponseDTO login(@Valid @RequestBody LoginDTO loginDto) throws BadCredentialsException {
    return new ResponseDTO(authService.login(loginDto));
  }

  /**
   * Handles bad credentials exceptions.
   * Send a response with status 401 UNAUTHORIZED.
   *
   * @param req current request
   * @param ex  bad credentials exception
   * @return an API error
   */
  @ExceptionHandler(BadCredentialsException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ErrorResponseDTO handleBadCredentialsException(HttpServletRequest req, Exception ex) {
    log.error("Authentication failed for bad credentials: {}", ex.getMessage());
    return new ErrorResponseDTO(ex);
  }
}
