package com.fvalle.company.exception;

import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiHandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionMessage> notFoundException(NotFoundException e){
        ExceptionMessage eM = ExceptionMessage.builder().code("GSS-400-003").message(e.getMessage()).timestamp(LocalDateTime.now()).build();
        return new ResponseEntity<>(eM, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ExceptionMessage> malFormedJwtException(MalformedJwtException e){
        ExceptionMessage eM = ExceptionMessage.builder().code("GSS-401-001").message(e.getMessage()).timestamp(LocalDateTime.now()).build();
        return new ResponseEntity<>(eM, HttpStatus.FORBIDDEN);
    }
}