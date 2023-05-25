package com.fvalle.company.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvalidNameException extends RuntimeException{

    private String code;
    private HttpStatus httpStatus;
}
