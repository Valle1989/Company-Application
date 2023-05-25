package com.fvalle.company.mapper;

import com.fvalle.company.dto.SupplierWithProductDto;
import com.fvalle.company.entity.Supplier;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface SupplierWithProductMapper {

    @Mappings({
            @Mapping(source = "id", target = "id_supplier"),
            @Mapping(source = "name", target = "supplierName"),
            @Mapping(source = "address", target = "supplierAddress"),
            @Mapping(source = "city", target = "location"),
            @Mapping(source = "phone", target = "contact"),
            @Mapping(source = "products", target = "supplierProducts")
    })
    SupplierWithProductDto toSupplierDto(Supplier supplier);
    List<SupplierWithProductDto> toSuppliersDto(List<Supplier> categoryList);

    @InheritInverseConfiguration
    Supplier toSupplier(SupplierWithProductDto supplierWithProductDto);
}
