package com.tusofia.transportify.transport.exception;

import com.tusofia.transportify.error.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TransportExceptionHandlerAdvice {

  @ExceptionHandler(TransportAlreadyExists.class)
  public ResponseEntity<Object> handleTransportAlreadyExistsException(TransportAlreadyExists ex) {
    return this.buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex));
  }

  private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }
}
