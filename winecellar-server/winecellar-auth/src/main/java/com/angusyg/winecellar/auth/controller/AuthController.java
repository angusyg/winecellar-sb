package com.angusyg.winecellar.auth.controller;

import com.angusyg.winecellar.auth.dto.LoginDto;
import com.angusyg.winecellar.auth.service.AuthService;
import com.angusyg.winecellar.core.web.controller.ApiController;
import com.angusyg.winecellar.core.web.dto.ApiResponse;
import com.angusyg.winecellar.core.web.dto.ErrorApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController extends ApiController {
  private static final String BAD_CREDENTIALS_ERROR_CODE = "BAD_CREDENTIALS";

  @Autowired
  private AuthService authService;

  @PostMapping("/signup")
  @Transactional(readOnly = true)
  public ApiResponse login(@Valid @RequestBody LoginDto loginDto) {
    return new ApiResponse(authService.login(loginDto));
  }

  @ExceptionHandler(BadCredentialsException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ErrorApiResponse handleBadCredentialsException(HttpServletRequest req, Exception ex) {
    log.error("Authentication failed for bad credentials: {}", ex.getMessage());
    return new ErrorApiResponse(BAD_CREDENTIALS_ERROR_CODE, ex.getMessage());
  }
}
