package com.fvalle.company.service;

import com.fvalle.company.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    List<ProductDto> getAll();
    Optional<List<ProductDto>> getByCategory(int categoryId);
    Optional<List<ProductDto>> getScarseProducts(int quantity);
    Optional<ProductDto> getProduct(int productId);
    ProductDto save(ProductDto productDto);
    public boolean delete(int productId);
}
