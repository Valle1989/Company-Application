package com.fvalle.company.exception;

import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApiHandlerException {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionMessageError> handleValidateException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();

            errors.put(fieldName, message);
        });

        ExceptionMessageError eme = ExceptionMessageError.builder().code("GSS-400-003")
                .message("All fields must be send")
                .timestamp(LocalDateTime.now())
                .errors(errors)
                .build();

        return new ResponseEntity<>(eme, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionMessage> notFoundException(NotFoundException e){
        ExceptionMessage eM = ExceptionMessage.builder().code("GSS-400-003").message(e.getMessage()).timestamp(LocalDateTime.now()).build();
        return new ResponseEntity<>(eM, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ExceptionMessage> malFormedJwtException(MalformedJwtException e){
        ExceptionMessage eM = ExceptionMessage.builder().code("GSS-401-001").message(e.getMessage()).timestamp(LocalDateTime.now()).build();
        return new ResponseEntity<>(eM, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidNameException.class)
    public ResponseEntity<ExceptionMessage> badRequestNameException(InvalidNameException e){
        ExceptionMessage eM = ExceptionMessage.builder().code(e.getCode()).message(e.getMessage()).timestamp(LocalDateTime.now()).build();
        return new ResponseEntity<>(eM,e.getHttpStatus());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionMessage> badRequestException(BadRequestException e){
        List<ErrorDetails> list = new ArrayList<>();
        e.getErrors().stream().forEach(err -> {
            ErrorDetails errorDetails = new ErrorDetails();
            errorDetails.setErrorCode(err.getErrorCode());
            errorDetails.setErrorMessage(err.getErrorMessage());
            list.add(errorDetails);
        });
        ExceptionMessage eM = ExceptionMessage.builder().code(e.getCode()).message(e.getMessage()).timestamp(LocalDateTime.now()).errors(list).build();
        return new ResponseEntity<>(eM, e.getHttpStatus());
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ExceptionMessage  returnErrorBadCredentialsException(BadCredentialsException e){
        return new ExceptionMessage(String.valueOf(HttpStatus.FORBIDDEN.value()), e.getMessage(), LocalDateTime.now());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailExistException.class)
    public ExceptionMessage handleEmailExist(HttpServletRequest request, EmailExistException e) {
        return new ExceptionMessage(String.valueOf(HttpStatus.BAD_REQUEST.value()), e.getMessage(), LocalDateTime.now());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CategoryExistException.class)
    public ExceptionMessage handleEmailExist(CategoryExistException e) {
        return new ExceptionMessage(String.valueOf(HttpStatus.BAD_REQUEST.value()), e.getMessage(), LocalDateTime.now());
    }
}
