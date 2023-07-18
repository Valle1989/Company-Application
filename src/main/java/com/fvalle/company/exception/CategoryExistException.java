package com.fvalle.company.exception;

public class CategoryExistException extends RuntimeException{

    private final static String CATEGORY_EXIST_MESSAGE = "This category already exists in the database: ";

    private static final long serialVersionUID = 1L;

    public CategoryExistException(String category) {
        super(CATEGORY_EXIST_MESSAGE + category);
    }
}
