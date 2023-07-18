package com.fvalle.company.mapper;

import com.fvalle.company.dto.CategoryDto;
import com.fvalle.company.dto.SupplierDto;
import com.fvalle.company.entity.Category;
import com.fvalle.company.entity.Supplier;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

    @Mappings({
            @Mapping(source = "id", target = "supplierId"),
            @Mapping(source = "name", target = "supplierName"),
            @Mapping(source = "address", target = "supplierAddress"),
            @Mapping(source = "city", target = "supplierCity"),
            @Mapping(source = "phone", target = "supplierPhone")
    })
    SupplierDto toSupplierDto(Supplier supplier);
    List<SupplierDto> toSuppliers(List<Supplier> supplierList);

    @InheritInverseConfiguration
    //@Mapping(target = "products", ignore = true)
    Supplier toSupplier(SupplierDto supplierDto);
}
