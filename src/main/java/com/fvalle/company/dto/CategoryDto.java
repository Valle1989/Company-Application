package com.fvalle.company.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {

    private int categoryId;
    private String category;
    private boolean active;
}
