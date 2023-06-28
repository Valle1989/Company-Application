package com.fvalle.company.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoryDto {

    private int categoryId;
    private String category;
    private boolean active;
}
