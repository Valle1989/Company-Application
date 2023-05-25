package com.fvalle.company.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SupplierWithProductDto {

    private Integer id_supplier;
    private String supplierName;
    private String supplierAddress;
    private String location;
    private String contact;
    private List<ProductDto> supplierProducts;
}
