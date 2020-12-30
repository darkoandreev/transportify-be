package com.tusofia.transportify.user.exceptions;

import com.tusofia.transportify.error.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

  @ExceptionHandler(UserAlreadyExistException.class)
  public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistException ex) {
    log.error("User already exists");
    return buildResponseEntity(new ApiError(HttpStatus.CONFLICT, ex.getMessage(), ex));
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
    log.error("User not found");
    return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex));
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex) {
    log.error("Username not found");
    return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex));
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
    String message = ex.getRootCause() != null && ex.getCause().getMessage() != null ? ex.getRootCause().getMessage() : ex.getMessage();
    return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, message, ex));
  }

  @ExceptionHandler(MissingRequestHeaderException.class)
  public ResponseEntity<Object> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
    return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex));
  }

  private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }
}
