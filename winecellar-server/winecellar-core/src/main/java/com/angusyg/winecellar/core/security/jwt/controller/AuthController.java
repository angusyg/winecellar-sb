package com.angusyg.winecellar.core.security.jwt.controller;

import com.angusyg.winecellar.core.security.jwt.dto.LoginDto;
import com.angusyg.winecellar.core.web.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("/auth")
public class AuthController {
  @Autowired
  private UserDetailsService userDetailsService;

  @PostMapping("/")
  public ApiResponse login(@Valid @RequestBody LoginDto loginDto) {
    userDetailsService.loadUserByUsername();
  }
}
