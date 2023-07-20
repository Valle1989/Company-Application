package com.fvalle.company.service;

import com.fvalle.company.dto.SupplierDto;
import com.fvalle.company.dto.SupplierWithProductDto;
import com.fvalle.company.entity.Supplier;

import java.util.List;

public interface ISupplierService {

    List<SupplierWithProductDto> getAll();
    List<SupplierDto> findAll();
    Supplier add(Supplier supplier);
    SupplierWithProductDto save(SupplierWithProductDto supplierWithProductDto);
    public boolean delete(int supplierId);
}
