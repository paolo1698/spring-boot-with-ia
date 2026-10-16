package com.example.springBootIA.controllers;

import com.example.springBootIA.exceptions.BusinessException;
import com.example.springBootIA.responses.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.springBootIA.exceptions.ErrorCode.UNKNOWN_ERROR;

@RestControllerAdvice
public class ExceptionHandlerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        ErrorResponse response = new ErrorResponse(
                ex.getMessage(),
                ex.getErrorCode().code(),
                null,
                //SessionContextHolder.getSession().correlationId(),
                ex.getParams().stream().map(String::valueOf).toArray(String[]::new)
        );
        LOGGER.error("A BusinessException occurred", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleException(Throwable ex) {
        ErrorResponse response = new ErrorResponse(
                ex.getMessage(),
                UNKNOWN_ERROR.code(),
                null//SessionContextHolder.getSession().correlationId()
        );
        LOGGER.error("An uncontrolled error occurred", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}