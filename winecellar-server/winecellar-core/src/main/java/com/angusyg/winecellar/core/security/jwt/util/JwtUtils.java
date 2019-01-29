package com.angusyg.winecellar.core.security.jwt.util;

import com.angusyg.winecellar.core.security.jwt.JwtTokenPayload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Helper to work with JWT Tokens.
 *
 * @since 0.0.1
 */
@Slf4j
@Component
public class JwtUtils {
  // JWT secret to sign token
  @Value("${security.jwt.secret}")
  private String secret;

  // User payload id key name
  private static final String USER_ID_KEY = "userId";

  // User payload roles key name
  private static final String ROLE_KEY = "roles";

  /**
   * Tries to parse specified String as a JWT token.
   * If successful, returns user payload.
   * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
   *
   * @param token the JWT token to parse
   * @return the User object extracted from specified token or null if a token is invalid.
   */
  public JwtTokenPayload parseToken(String token) {
    try {
      // Decodes token
      Claims body = Jwts.parser()
          .setSigningKey(secret)
          .parseClaimsJws(token)
          .getBody();

      // Creates payload form decoded token body
      JwtTokenPayload jwtTokenPayload = new JwtTokenPayload();
      jwtTokenPayload.setUsername(body.getSubject());
      jwtTokenPayload.setId((String) body.get(USER_ID_KEY));
      jwtTokenPayload.setRoles((String) body.get(ROLE_KEY));

      return jwtTokenPayload;
    } catch (JwtException | ClassCastException ex) {
      log.error("Exception while decoding JWT Token: {}", ex.getMessage());
      return null;
    }
  }

  /**
   * Generates a JWT token containing username as subject and additional claims.
   * These properties are taken from a specified payload.
   *
   * @param payload the user payload
   * @return the JWT token
   */
  public String generateToken(JwtTokenPayload payload) {
    // Creates token body
    Claims claims = Jwts.claims().setSubject(payload.getUsername());
    claims.put(USER_ID_KEY, payload.getId() + "");
    claims.put(ROLE_KEY, payload.getRoles());

    // Encodes and signs token
    return Jwts.builder()
        .setClaims(claims)
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }
}
