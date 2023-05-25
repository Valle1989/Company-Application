package com.fvalle.company.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BadRequestException extends RuntimeException{

    private String code;
    private HttpStatus httpStatus;
    private List<ErrorDetails> errors;


    public BadRequestException(String code, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public BadRequestException(String code,HttpStatus httpStatus,String message, List<ErrorDetails> errors) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
        this.errors = errors;
    }
}
