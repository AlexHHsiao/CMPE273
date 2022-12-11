package com.example.cpme273.wlb.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class WlbErrorHandler extends ResponseEntityExceptionHandler {
  private final MessageSource messageSource;

  public WlbErrorHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    List<String> errors = new ArrayList<String>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.add(error.getField() + " " + error.getDefaultMessage());
    }
    for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
      errors.add(error.getObjectName() + " " + error.getDefaultMessage());
    }

    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Invalid content in request", errors);
    return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
  }

  @ExceptionHandler({DataExistsException.class})
  public ResponseEntity<Object> handleDataExistsException(Exception ex, WebRequest request) {

    ApiError apiError =
        new ApiError(
            HttpStatus.CONFLICT, ex.getLocalizedMessage(), DataExistsException.class.getSimpleName());
    return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
  }

  @ExceptionHandler({DataNotFoundException.class})
  public ResponseEntity<Object> handleDataNotFoundException(Exception ex, WebRequest request) {

    ApiError apiError =
        new ApiError(
            HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), DataNotFoundException.class.getSimpleName());
    return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
  }

  @ExceptionHandler({IllegalArgumentException.class})
  public ResponseEntity<Object> handleIllegalArgumentException(Exception ex, WebRequest request) {

    ApiError apiError =
        new ApiError(
            HttpStatus.BAD_REQUEST,
            ex.getLocalizedMessage(),
            IllegalArgumentException.class.getSimpleName());
    return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
  }

  @ExceptionHandler({AuthenticationException.class})
  public ResponseEntity<Object> handleAuthenticationException(Exception ex, WebRequest request) {

    ApiError apiError =
            new ApiError(
                    HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage(), AuthenticationException.class.getSimpleName());
    return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
  }

  @ExceptionHandler({AuthorizationException.class})
  public ResponseEntity<Object> handleAuthorizationException(Exception ex, WebRequest request) {

    ApiError apiError =
            new ApiError(
                    HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage(), AuthorizationException.class.getSimpleName());
    return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
  }

  @ExceptionHandler({InternalServiceException.class})
  public ResponseEntity<Object> handleInternalServiceException(Exception ex, WebRequest request) {

    ApiError apiError =
            new ApiError(
                    HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage(), InternalServiceException.class.getSimpleName());
    return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
  }

  private String getLocalizedMessage(FieldError fieldError) {
    return this.messageSource.getMessage(fieldError, this.getCurrentLocale());
  }

  private Locale getCurrentLocale() {
    return LocaleContextHolder.getLocale();
  }
}
