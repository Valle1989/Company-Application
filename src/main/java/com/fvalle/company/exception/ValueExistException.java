package com.fvalle.company.exception;

public class ValueExistException extends RuntimeException{

    private final static String VALUE_EXIST_MESSAGE = "This value already exists in the database: ";

    private static final long serialVersionUID = 1L;

    public ValueExistException(String value) {
        super(VALUE_EXIST_MESSAGE + value);
    }
}
