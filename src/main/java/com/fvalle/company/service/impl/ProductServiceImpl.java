package com.fvalle.company.service.impl;

import com.fvalle.company.dto.ProductDto;
import com.fvalle.company.entity.Product;
import com.fvalle.company.exception.NotFoundException;
import com.fvalle.company.mapper.ProductMapper;
import com.fvalle.company.repository.ProductRepository;
import com.fvalle.company.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDto> getAll() {
        List<Product> products = (List<Product>) productRepository.findAll();
        return productMapper.toProductsDto(products);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<List<ProductDto>> getByCategory(int categoryId) {
        List<Product> products = productRepository.findByIdCategoryOrderByNameAsc(categoryId);
        return Optional.of(productMapper.toProductsDto(products));
    }

    @Override
    public Optional<List<ProductDto>> getScarseProducts(int quantity) {
        Optional<List<Product>> products = productRepository.findByStockQuantityLessThanAndState(quantity,true);
        return products.map(prods -> productMapper.toProductsDto(prods));
    }

    @Override
    public Optional<ProductDto> getProduct(int productId) {
        Optional<Product> product = productRepository.findById(productId);
        if(!product.isPresent()){
            throw new NotFoundException("Error, id cannot be found");
        }else{
            return product.map(prod -> productMapper.toProductDto(prod));
        }
        //return productRepository.findById(productId).map(prod -> productMapper.toProduct(prod));
    }

    @Override
    @Transactional
    public ProductDto save(ProductDto productDto) {
        Product product = productMapper.toProduct(productDto);
        return productMapper.toProductDto(productRepository.save(product));
    }

    @Override
    @Transactional
    public ProductDto update(int id, ProductDto productDto) {
        Product product = productRepository
                .findById(id).orElseThrow(() -> new NotFoundException("Product id not found"));
        Product productUpdated = mapDtoToEntityUpdate(product,productDto);
        productRepository.save(productUpdated);
        return productMapper.toProductDto(productUpdated);
    }

    private Product mapDtoToEntityUpdate(Product product, ProductDto productDto){
        product.setName(productDto.getName());
        product.setIdCategory(productDto.getCategoryId());
        product.setSellingPrice(productDto.getPrice());
        product.setStockQuantity(productDto.getStock());
        product.setState(productDto.isActive());
        product.setSupplierId(productDto.getIdSupplier());
        return product;
    }

    @Override
    @Transactional
    public boolean delete(int productId) {
        return getProduct(productId).map(product -> {
            productRepository.deleteById(productId);
            return true;
        }).orElse(false);
    }
}
