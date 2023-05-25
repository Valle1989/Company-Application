package com.fvalle.company.mapper;

import com.fvalle.company.dto.ProductDto;
import com.fvalle.company.entity.Product;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {

    @Mappings({
            //@Mapping(source = "idProduct", target = "productId"),
            @Mapping(source = "idCategory", target = "categoryId"),
            @Mapping(source = "sellingPrice", target = "price"),
            @Mapping(source = "stockQuantity", target = "stock"),
            @Mapping(source = "state", target = "active"),
            @Mapping(source = "supplierId", target = "idSupplier"),
            @Mapping(source = "category", target = "categoryDto"),
    })
    ProductDto toProductDto(Product product);
    List<ProductDto> toProductsDto(List<Product> products);

    @InheritInverseConfiguration
    @Mapping(target = "barCode", ignore = true)
    Product toProduct(ProductDto productDto);
}
