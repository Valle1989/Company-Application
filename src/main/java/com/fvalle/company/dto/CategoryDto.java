package com.fvalle.company.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoryDto {

    private int categoryId;
    @NotNull(message = "category field cannot be null")
    private String category;
    @NotNull(message = "active field cannot be null")
    private boolean active;
}
