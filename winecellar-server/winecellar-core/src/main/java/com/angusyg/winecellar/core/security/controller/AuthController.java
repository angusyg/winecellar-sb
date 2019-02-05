package com.angusyg.winecellar.core.security.controller;

import com.angusyg.winecellar.core.exception.ApiException;
import com.angusyg.winecellar.core.security.dto.LoginDTO;
import com.angusyg.winecellar.core.security.dto.SignupDTO;
import com.angusyg.winecellar.core.security.dto.TokenDTO;
import com.angusyg.winecellar.core.security.service.AuthService;
import com.angusyg.winecellar.core.web.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
   * Sign up endpoint.
   *
   * @param signupDTO signup infos
   * @return a {@link TokenDTO} containing JWT Token
   */
  @PostMapping("/signup")
  public TokenDTO signup(@Valid @RequestBody SignupDTO signupDTO) throws ApiException {
    return new TokenDTO(authService.signup(signupDTO));
  }

  /**
   * Login endpoint.
   *
   * @param loginDto login infos (username and password)
   * @return a {@link TokenDTO} containing JWT Token if login is ok
   */
  @PostMapping("/login")
  public TokenDTO login(@Valid @RequestBody LoginDTO loginDto) throws ApiException {
    return new TokenDTO(authService.login(loginDto));
  }
}
