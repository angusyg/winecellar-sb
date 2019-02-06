package com.angusyg.winecellar.core.web.controller;

import com.angusyg.winecellar.core.exception.ExceptionCode;
import com.angusyg.winecellar.core.web.dto.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
public class BaseErrorController extends AbstractErrorController {
  // Error path
  private static final String PATH = "/error";
  // Status code attribute
  private static final String STATUS_CODE_ATTRIBUTE = "javax.servlet.error.status_code";

  /**
   * Creates an instance of {@link BaseErrorController} with default error attributes.
   *
   * @param errorAttributes default error attributes.
   */
  public BaseErrorController(ErrorAttributes errorAttributes) {
    super(errorAttributes);
  }

  /**
   * Handle dispatcher error mapping default error path.
   *
   * @param request
   * @return
   */
  @RequestMapping(PATH)
  public ErrorResponseDTO error(HttpServletRequest request) {
    Map<String ,Object> attributes = getErrorAttributes(request, true);
    switch ((int) attributes.get("status")) {
      case 404:
        return new ErrorResponseDTO(ExceptionCode.NOT_FOUND, String.format("URL '%s' does not exist", attributes.get("path")));
      default:
        return new ErrorResponseDTO();
    }
  }

  @Override
  public String getErrorPath() {
    return PATH;
  }
}
