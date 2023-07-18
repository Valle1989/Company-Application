package com.fvalle.company.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDto {

    private Integer supplierId;
    private String supplierName;
    private String supplierAddress;
    private String supplierCity;
    private String supplierPhone;
}
