package com.angusyg.winecellar.core.web.filter;

import com.angusyg.winecellar.core.exception.ApiException;
import com.angusyg.winecellar.core.web.dto.ErrorResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filters extending this filter are given a method to handle {@link ApiException}.
 * Handling means parsing and sending the right response according to exception.
 *
 * @since 0.0.1
 */
@Slf4j
public abstract class ApiOncePerRequestFilter extends OncePerRequestFilter {
  /**
   * Handles {@link ApiException} creating and sending corresponding response
   *
   * @param apiException exception to handle
   * @param response     response to send
   */
  protected void handleApiException(ApiException apiException, HttpServletResponse response) {
    try {
      // Creates response from exception
      ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(apiException);
      // Sets response status from api exception code
      response.setStatus(apiException.getCode().getValue().value());
      // Serialize response body
      response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponseDTO));
    } catch (IOException ex) {
      // Log and send internal error with no body
      log.error("Exception occurred while serializing error response in filter: {}", ex.getMessage(), ex);
      response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
  }
}
