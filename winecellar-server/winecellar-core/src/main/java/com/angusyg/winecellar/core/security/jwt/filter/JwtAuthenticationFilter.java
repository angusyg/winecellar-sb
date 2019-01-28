package com.angusyg.winecellar.core.security.jwt.filter;

import com.angusyg.winecellar.core.security.jwt.exception.JwtTokenMissingException;
import com.angusyg.winecellar.core.security.jwt.token.JwtAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String AUTHORIZATION_SCHEME = "Bearer ";

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
    // Gets authorization header to get JWT value
    String header = request.getHeader(AUTHORIZATION_HEADER);

    if (header == null || !header.startsWith(AUTHORIZATION_SCHEME)) {
      // If no authorization header or with bad scheme, throw exception
      throw new JwtTokenMissingException("No JWT token found in request headers");
    }

    // Gets JWT value
    String authToken = header.substring(AUTHORIZATION_SCHEME.length());

    // Creates auth token from header
    JwtAuthenticationToken authRequest = new JwtAuthenticationToken(authToken);

    // Authentication user with JWT auth token
    return getAuthenticationManager().authenticate(authRequest);
  }
}
