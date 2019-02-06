package com.angusyg.winecellar.core.security.web;

import com.angusyg.winecellar.core.exception.ExceptionCode;
import com.angusyg.winecellar.core.web.dto.ErrorResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Authentication entry point for API. Returns a JSON with 401 status code.
 *
 * @since 0.0.1
 */
public class AuthEntryPoint implements AuthenticationEntryPoint {
  /**
   * Send a 401 response with a JSON in body.
   *
   * @param request       current request
   * @param response      current response
   * @param authException authentication exception
   * @throws IOException when an error occurred during body writing
   */
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
    // Create response.
    ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(ExceptionCode.UNAUTHORIZED);
    // Set response status from api exception code.
    response.setStatus(errorResponseDTO.getCode().getValue().value());
    // Serialize response body.
    response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponseDTO));
    // Set type of response as JSON
    response.setContentType(MediaType.APPLICATION_JSON.toString());
  }
}
