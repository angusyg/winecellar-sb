package com.angusyg.winecellar.core.security.jwt.util;

import com.angusyg.winecellar.core.security.jwt.JwtTokenPayload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtils {
  @Value("${security.jwt.secret}")
  private String secret;

  /**
   * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted from token).
   * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
   *
   * @param token the JWT token to parse
   * @return the User object extracted from specified token or null if a token is invalid.
   */
  public JwtTokenPayload parseToken(String token) {
    try {
      Claims body = Jwts.parser()
          .setSigningKey(secret)
          .parseClaimsJws(token)
          .getBody();

      JwtTokenPayload jwtTokenPayload = new JwtTokenPayload();
      jwtTokenPayload.setUsername(body.getSubject());
      jwtTokenPayload.setId((String) body.get("userId"));
      jwtTokenPayload.setRoles((String) body.get("roles"));

      return jwtTokenPayload;
    } catch (JwtException | ClassCastException ex) {
      log.error("Exception while decoding JWT Token: {}", ex.getMessage());
      return null;
    }
  }

  /**
   * Generates a JWT token containing username as subject, and userId and role as additional claims. These properties are taken from the specified
   * User object. Tokens validity is infinite.
   *
   * @param payload the user payload encoded in JWT token
   * @return the JWT token
   */
  public String generateToken(JwtTokenPayload payload) {
    Claims claims = Jwts.claims().setSubject(payload.getUsername());
    claims.put("userId", payload.getId() + "");
    claims.put("roles", payload.getRoles());

    return Jwts.builder()
        .setClaims(claims)
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }
}
