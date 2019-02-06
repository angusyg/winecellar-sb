package com.angusyg.winecellar.core.security.jwt.filter;

import com.angusyg.winecellar.core.exception.ApiException;
import com.angusyg.winecellar.core.security.jwt.configuration.JwtConfiguration;
import com.angusyg.winecellar.core.security.jwt.dto.JwtTokenPayloadDTO;
import com.angusyg.winecellar.core.security.jwt.util.JwtUtils;
import com.angusyg.winecellar.core.web.filter.ApiOncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter to auto authenticate user if a valid JWT Token is found in request header.
 *
 * @since 0.0.1
 */
@Component
public class JwtTokenAuthenticationFilter extends ApiOncePerRequestFilter {
  // JWT security configuration
  private JwtConfiguration jwtConfiguration;

  // Util for JWT Token operations
  private JwtUtils jwtUtils;

  /**
   * Creates an instance of {@link JwtTokenAuthenticationFilter}
   *
   * @param jwtConfiguration
   * @param jwtUtils
   */
  public JwtTokenAuthenticationFilter(JwtConfiguration jwtConfiguration, JwtUtils jwtUtils) {
    this.jwtConfiguration = jwtConfiguration;
    this.jwtUtils = jwtUtils;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
    // Get the authentication header where tokens are supposed to be passed.
    String header = request.getHeader(jwtConfiguration.getHeader());
    // If authorization header is not found or prefix value is not the one expected, go to the next filter.
    if (header == null || !header.startsWith(jwtConfiguration.getPrefix())) {
      chain.doFilter(request, response);
    } else {
      // Get the token value from header and remove prefix from it.
      String token = header.replace(jwtConfiguration.getPrefix(), "");
      try {
        // Validate the token and extracts its payload.
        JwtTokenPayloadDTO payload = jwtUtils.parseToken(token);
        // Create auth object.
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(payload.getUsername(), null, AuthorityUtils.commaSeparatedStringToAuthorityList(payload.getAuthorities()));
        // Authenticate the user.
        SecurityContextHolder.getContext().setAuthentication(auth);
      } catch (ApiException e) {
        // In case of failure. Make sure it's clear; so guarantee user won't be authenticated.
        SecurityContextHolder.clearContext();
      }
      // Go to the next filter.
      chain.doFilter(request, response);
    }
  }
}
