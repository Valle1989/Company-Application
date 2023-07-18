package com.fvalle.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateDto {

    private String name;
    private int categoryId;
    private double price;
    private int stock;
    private boolean active;
    private int idSupplier;
}
