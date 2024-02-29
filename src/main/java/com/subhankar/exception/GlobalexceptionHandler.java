package com.subhankar.exception;

import com.subhankar.model.DTO.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalexceptionHandler extends RuntimeException{

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Result> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Result result = Result.builder()
                .message(ex.getMessage())
                .status("error")
                .data(null)
                .build();
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(java.lang.IllegalArgumentException.class)
    public ResponseEntity<Result> handleIllegalArgumentException(java.lang.IllegalArgumentException ex) {
        Result result = Result.builder()
                .message(ex.getMessage())
                .status("error")
                .data(null)
                .build();
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Result> handleUnauthorizedException(UnauthorizedException ex) {
        Result result = Result.builder()
                .message(ex.getMessage())
                .status("error")
                .data(null)
                .build();
        return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result> handleException(Exception ex) {
        Result result = Result.builder()
                .message(ex.getMessage())
                .status("error")
                .data(null)
                .build();
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
