package com.fvalle.company.exception;

import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionMessageError {

    private String code;
    private String message;
    private LocalDateTime timestamp;
    private Map<String,String> errors = new HashMap<>();
}
