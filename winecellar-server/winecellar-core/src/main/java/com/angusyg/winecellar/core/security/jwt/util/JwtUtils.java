package com.angusyg.winecellar.core.security.jwt.util;

import com.angusyg.winecellar.core.exception.ApiException;
import com.angusyg.winecellar.core.exception.ExceptionCode;
import com.angusyg.winecellar.core.security.jwt.dto.JwtTokenPayloadDTO;
import io.jsonwebtoken.*;
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
  // User payload id key name
  private static final String USER_ID_KEY = "userId";
  // User payload authorities key name
  private static final String AUTHORITIES_KEY = "authorities";
  // JWT secret to sign token
  @Value("${security.jwt.secret}")
  private String secret;

  /**
   * Try to parse specified String as a JWT token.
   * If successful, returns user payload.
   * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
   *
   * @param token the JWT token to parse
   * @return the User object extracted from specified token or null if a token is invalid.
   * @throws ApiException when an error occurred while parsing token
   */
  public JwtTokenPayloadDTO parseToken(String token) throws ApiException {
    try {
      // Decode token
      Claims body = Jwts.parser()
          .setSigningKey(secret)
          .parseClaimsJws(token)
          .getBody();
      // Create payload form decoded token body
      JwtTokenPayloadDTO jwtTokenPayloadDTO = new JwtTokenPayloadDTO();
      jwtTokenPayloadDTO.setUsername(body.getSubject());
      jwtTokenPayloadDTO.setId((String) body.get(USER_ID_KEY));
      jwtTokenPayloadDTO.setAuthorities((String) body.get(AUTHORITIES_KEY));
      return jwtTokenPayloadDTO;
    } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException ex) {
      log.error("Exception while decoding JWT Token: {}", ex.getMessage(), ex);
      if (ex instanceof ExpiredJwtException) {
        throw new ApiException(ExceptionCode.EXPIRED_JWT_TOKEN);
      } else if(ex instanceof UnsupportedJwtException) {
        throw new ApiException(ExceptionCode.UNSUPPORTED_JWT_TOKEN);
      } else if(ex instanceof MalformedJwtException) {
        throw new ApiException(ExceptionCode.MALFORMED_JWT_TOKEN);
      } else if(ex instanceof SignatureException) {
        throw new ApiException(ExceptionCode.SIGNATURE_JWT_TOKEN);
      }
      return null;
    }
  }

  /**
   * Generate a JWT token containing username as subject and additional claims.
   * These properties are taken from a specified payload.
   *
   * @param payload the user payload
   * @return the JWT token
   */
  public String generateToken(JwtTokenPayloadDTO payload) {
    // Create token body
    Claims claims = Jwts.claims().setSubject(payload.getUsername());
    claims.put(USER_ID_KEY, payload.getId() + "");
    claims.put(AUTHORITIES_KEY, payload.getAuthorities());
    // Encode and sign token
    return Jwts.builder()
        .setClaims(claims)
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }
}
