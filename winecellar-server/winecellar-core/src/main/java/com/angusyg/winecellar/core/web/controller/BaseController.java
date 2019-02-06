package com.angusyg.winecellar.core.web.controller;

import com.angusyg.winecellar.core.exception.ApiException;
import com.angusyg.winecellar.core.exception.ExceptionCode;
import com.angusyg.winecellar.core.web.dto.ErrorResponseDTO;
import com.angusyg.winecellar.core.web.dto.ValidationErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * API base controller
 * Handles uncaught exceptions to return API errors
 *
 * @since 0.0.1
 */
@Slf4j
public class BaseController {
  /**
   * Handles duplicate data exceptions.
   *
   * @param req current request
   * @param ex  bad credentials exception
   * @return an API error
   */
  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ErrorResponseDTO> handleApiException(HttpServletRequest req, ApiException ex) {
    log.error("Sending error for API Exception: {}", ex.getMessage(), ex);
    return responseEntityFromErrorResponseDTO(new ErrorResponseDTO(ex));
  }

  /**
   * Handles multiple standard exceptions.
   *
   * @param ex      exception to handle
   * @param request current request
   * @return an API error response
   * @throws Exception throw if no suitable handling method found for given exception
   */
  @ExceptionHandler({
      HttpRequestMethodNotSupportedException.class,
      HttpMediaTypeNotSupportedException.class,
      HttpMediaTypeNotAcceptableException.class,
      MissingPathVariableException.class,
      MissingServletRequestParameterException.class,
      ServletRequestBindingException.class,
      ConversionNotSupportedException.class,
      TypeMismatchException.class,
      HttpMessageNotReadableException.class,
      HttpMessageNotWritableException.class,
      MethodArgumentNotValidException.class,
      MissingServletRequestPartException.class,
      BindException.class,
      NoHandlerFoundException.class,
      AsyncRequestTimeoutException.class,
      AccessDeniedException.class
  })
  public ResponseEntity<ErrorResponseDTO> handleCommonExceptions(Exception ex, HttpServletRequest request) throws Exception {
    log.error("Sending error for {} exception: {}", ex.getClass(), ex.getMessage(), ex);
    if (ex instanceof HttpRequestMethodNotSupportedException) {
      return handleInternalException(ExceptionCode.METHOD_NOT_ALLOWED);
    } else if (ex instanceof HttpMediaTypeNotSupportedException) {
      return handleInternalException(ExceptionCode.UNSUPPORTED_MEDIA_TYPE);
    } else if (ex instanceof HttpMediaTypeNotAcceptableException) {
      return handleInternalException(ExceptionCode.NOT_ACCEPTABLE);
    } else if (ex instanceof MissingPathVariableException) {
      return handleInternalException(ExceptionCode.INTERNAL_SERVER_ERROR);
    } else if (ex instanceof MissingServletRequestParameterException) {
      return handleInternalException(ExceptionCode.BAD_REQUEST);
    } else if (ex instanceof ServletRequestBindingException) {
      return handleInternalException(ExceptionCode.BAD_REQUEST);
    } else if (ex instanceof ConversionNotSupportedException) {
      return handleInternalException(ExceptionCode.INTERNAL_SERVER_ERROR);
    } else if (ex instanceof TypeMismatchException) {
      return handleInternalException(ExceptionCode.BAD_REQUEST);
    } else if (ex instanceof HttpMessageNotReadableException) {
      return handleInternalException(ExceptionCode.BAD_REQUEST);
    } else if (ex instanceof HttpMessageNotWritableException) {
      return handleInternalException(ExceptionCode.INTERNAL_SERVER_ERROR);
    } else if (ex instanceof MethodArgumentNotValidException) {
      return handleMethodArgumentNotValidException((MethodArgumentNotValidException) ex);
    } else if (ex instanceof MissingServletRequestPartException) {
      return handleInternalException(ExceptionCode.BAD_REQUEST);
    } else if (ex instanceof BindException) {
      return handleInternalException(ExceptionCode.BAD_REQUEST);
    } else if (ex instanceof NoHandlerFoundException) {
      return handleInternalException(ExceptionCode.NOT_FOUND);
    } else if (ex instanceof AsyncRequestTimeoutException) {
      return handleInternalException(ExceptionCode.SERVICE_UNAVAILABLE);
    } else if(ex instanceof  AccessDeniedException) {
      return handleInternalException(ExceptionCode.FORBIDDEN);
    } else {
      log.error("Unhandled exception of type {}: {}", ex.getClass(), ex.getMessage(), ex);
      throw ex;
    }
  }

  /**
   * Handles uncaught exceptions to return a standard API error.
   *
   * @param req current request
   * @param ex  uncaught exception
   * @return an API error response
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseDTO> handleException(HttpServletRequest req, Exception ex) {
    log.error("Sending error for uncaught exception: {}", ex.getMessage(), ex);
    return responseEntityFromErrorResponseDTO(new ErrorResponseDTO());
  }

  /**
   * Handles validation errors on argument validation.
   *
   * @param ex validation exception
   * @return an API error response
   */
  private ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    BindingResult result = ex.getBindingResult();
    List<FieldError> fieldErrors = result.getFieldErrors();
    ValidationErrorDTO validationErrorDto = new ValidationErrorDTO(fieldErrors);
    return responseEntityFromErrorResponseDTO(new ErrorResponseDTO(ExceptionCode.VALIDATION_ERROR, null, validationErrorDto));
  }

  /**
   * Handles error and create response from {@link HttpStatus}.
   *
   * @param exceptionCode {@link ExceptionCode} exception code
   * @return an API error response
   */
  private ResponseEntity<ErrorResponseDTO> handleInternalException(ExceptionCode exceptionCode) {
    return responseEntityFromErrorResponseDTO(new ErrorResponseDTO(exceptionCode));
  }

  /**
   * Creates a response entity from an {@link ErrorResponseDTO} instance.
   *
   * @param errorResponseDTO instance of {@link ErrorResponseDTO} to send
   * @return a {@link ResponseEntity} instance with given {@link ErrorResponseDTO} as body and {@link ExceptionCode} http status.
   */
  private ResponseEntity<ErrorResponseDTO> responseEntityFromErrorResponseDTO(ErrorResponseDTO errorResponseDTO) {
    return new ResponseEntity<>(errorResponseDTO, errorResponseDTO.getCode().getValue());
  }
}
