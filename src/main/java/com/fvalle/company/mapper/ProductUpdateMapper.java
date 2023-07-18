package com.fvalle.company.mapper;

import com.fvalle.company.dto.ProductDto;
import com.fvalle.company.dto.ProductUpdateDto;
import com.fvalle.company.entity.Product;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductUpdateMapper {

    @Mappings({
            @Mapping(source = "idCategory", target = "categoryId"),
            @Mapping(source = "sellingPrice", target = "price"),
            @Mapping(source = "stockQuantity", target = "stock"),
            @Mapping(source = "state", target = "active"),
            @Mapping(source = "supplierId", target = "idSupplier")
    })
    ProductUpdateDto toProductUpdateDto(Product product);
    List<ProductUpdateDto> toProductsDto(List<Product> products);

    @InheritInverseConfiguration
    Product toProduct(ProductUpdateDto productUpdateDto);
}
