package com.angusyg.winecellar.core.web.controller;

import com.angusyg.winecellar.core.web.dto.ErrorApiResponse;
import com.angusyg.winecellar.core.web.validation.ValidationErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
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
public class ApiController {
  // Validation error code for API error response
  private static final String VALIDATION_ERROR_CODE = "VALIDATION_ERROR";

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
      AsyncRequestTimeoutException.class
  })
  public ResponseEntity<ErrorApiResponse> handleHttpError(Exception ex, HttpServletRequest request) throws Exception {
    if (ex instanceof HttpRequestMethodNotSupportedException) {
      return handleInternalException(HttpStatus.METHOD_NOT_ALLOWED);
    } else if (ex instanceof HttpMediaTypeNotSupportedException) {
      return handleInternalException(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    } else if (ex instanceof HttpMediaTypeNotAcceptableException) {
      return handleInternalException(HttpStatus.NOT_ACCEPTABLE);
    } else if (ex instanceof MissingPathVariableException) {
      return handleInternalException(HttpStatus.INTERNAL_SERVER_ERROR);
    } else if (ex instanceof MissingServletRequestParameterException) {
      return handleInternalException(HttpStatus.BAD_REQUEST);
    } else if (ex instanceof ServletRequestBindingException) {
      return handleInternalException(HttpStatus.BAD_REQUEST);
    } else if (ex instanceof ConversionNotSupportedException) {
      return handleInternalException(HttpStatus.INTERNAL_SERVER_ERROR);
    } else if (ex instanceof TypeMismatchException) {
      return handleInternalException(HttpStatus.BAD_REQUEST);
    } else if (ex instanceof HttpMessageNotReadableException) {
      return handleInternalException(HttpStatus.BAD_REQUEST);
    } else if (ex instanceof HttpMessageNotWritableException) {
      return handleInternalException(HttpStatus.INTERNAL_SERVER_ERROR);
    } else if (ex instanceof MethodArgumentNotValidException) {
      return handleMethodArgumentNotValidException((MethodArgumentNotValidException) ex);
    } else if (ex instanceof MissingServletRequestPartException) {
      return handleInternalException(HttpStatus.BAD_REQUEST);
    } else if (ex instanceof BindException) {
      return handleInternalException(HttpStatus.BAD_REQUEST);
    } else if (ex instanceof NoHandlerFoundException) {
      return handleInternalException(HttpStatus.NOT_FOUND);
    } else if (ex instanceof AsyncRequestTimeoutException) {
      return handleInternalException(HttpStatus.SERVICE_UNAVAILABLE);
    } else {
      // If exception is an instance of an exception not handle by this method
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
  public ResponseEntity<ErrorApiResponse> handleException(HttpServletRequest req, Exception ex) {
    log.error("Sending API error for uncaught exception: {}", ex.getMessage());
    return new ResponseEntity<>(new ErrorApiResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Handles validation errors on argument validation.
   *
   * @param ex validation exception
   * @return an API error response
   */
  private ResponseEntity<ErrorApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    BindingResult result = ex.getBindingResult();
    List<FieldError> fieldErrors = result.getFieldErrors();
    ValidationErrorDto validationErrorDto = new ValidationErrorDto(fieldErrors);
    return new ResponseEntity<>(new ErrorApiResponse(HttpStatus.BAD_REQUEST, VALIDATION_ERROR_CODE, validationErrorDto), HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles error and create response from {@link HttpStatus}.
   *
   * @param status {@link HttpStatus} of response
   * @return an API error response
   */
  private ResponseEntity<ErrorApiResponse> handleInternalException(HttpStatus status) {
    return new ResponseEntity<>(new ErrorApiResponse(status), status);
  }
}
