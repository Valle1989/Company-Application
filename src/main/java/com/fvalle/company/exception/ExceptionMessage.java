package com.fvalle.company.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionMessage {

    private String code;
    private String message;
    private LocalDateTime timestamp;
    //private List<ErrorDetails> errors = new ArrayList();



    public ExceptionMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ExceptionMessage(String code, String message, LocalDateTime timestamp) {
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
    }

    /*public ExceptionMessage(String code, String message, LocalDateTime timestamp, List<ErrorDetails> errors) {
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
        this.errors = errors;
    }*/
}
