package com.angusyg.winecellar.core.security.jwt.service;

import com.angusyg.winecellar.core.security.jwt.dto.LoginDto;
import com.angusyg.winecellar.core.security.jwt.token.JwtAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {
  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  UserDetailsService userDetailsService;

  public JwtAuthenticationToken login(LoginDto loginDto) {
    JwtAuthenticationToken jwtAuthenticationToken = null;
    try {
      UserDetails user = userDetailsService.loadUserByUsername(loginDto.getUsername());
      if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
        jwtAuthenticationToken = new JwtAuthenticationToken();
      }

      return jwtAuthenticationToken;
    } catch (UsernameNotFoundException ex) {
      log.error("Login failed for user with username '{}'", loginDto.getUsername());
      return jwtAuthenticationToken;
    }
  }
}
