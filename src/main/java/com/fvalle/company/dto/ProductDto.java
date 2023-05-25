package com.fvalle.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {

    private String name;
    private int categoryId;
    private double price;
    private int stock;
    private boolean active;
    private int idSupplier;
    private CategoryDto categoryDto;

}
