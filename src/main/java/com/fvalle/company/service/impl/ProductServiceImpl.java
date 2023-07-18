package com.fvalle.company.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fvalle.company.dto.ProductDto;
import com.fvalle.company.dto.ProductUpdateDto;
import com.fvalle.company.entity.Employee;
import com.fvalle.company.entity.Product;
import com.fvalle.company.exception.BadRequestException;
import com.fvalle.company.exception.ErrorDetails;
import com.fvalle.company.exception.NotFoundException;
import com.fvalle.company.mapper.ProductMapper;
import com.fvalle.company.mapper.ProductUpdateMapper;
import com.fvalle.company.repository.ProductRepository;
import com.fvalle.company.service.IProductService;
import com.fvalle.company.utils.CheckNullField;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    private final ProductUpdateMapper productUpdateMapper;

    @Override
    public List<ProductDto> getAll() {
        List<Product> products = productRepository.findAll();
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
        return products.map(productMapper::toProductsDto);
    }

    @Override
    public Optional<ProductDto> getProduct(int productId) {
        Optional<Product> product = productRepository.findById(productId);
        if(!product.isPresent()){
            throw new NotFoundException("Error, id cannot be found");
        }else{
            return product.map(productMapper::toProductDto);
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

    @Override
    @Transactional
    public ProductUpdateDto updateProductWithDto(int id, ProductUpdateDto productUpdateDto) {
        Product product = productRepository
                .findById(id).orElseThrow(() -> new NotFoundException("Product id not found"));

        System.out.println(product.getName().getClass());

        List<Product> products = Arrays.asList(product);

        Map<String, Object> map = new HashMap<>();

        products.stream()
                .forEach(prod -> {
                    map.put("name", prod.getName());
                    map.put("idCategory", prod.getIdCategory());
                    map.put("sellingPrice", prod.getSellingPrice());
                    map.put("stockQuantity", prod.getStockQuantity());
                    map.put("state", prod.getState());
                    map.put("supplierId", prod.getSupplierId());
                });

        List<ErrorDetails> list = new ArrayList<>();
        Product getProduct = productUpdateMapper.toProduct(productUpdateDto);

        System.out.println("--------------------");
        System.out.println(getProduct.getName().getClass());

        List<Product> productList = new ArrayList<>();
        productList.add(getProduct);
        Map<String, Object> productMap = new HashMap<>();

        productList.stream()
                .forEach(prod -> {
                    productMap.put("name", prod.getName());
                    productMap.put("idCategory", prod.getIdCategory());
                    productMap.put("sellingPrice", prod.getSellingPrice());
                    productMap.put("stockQuantity", prod.getStockQuantity());
                    productMap.put("state", prod.getState());
                    productMap.put("supplierId", prod.getSupplierId());
                });

        /*ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(productUpdateDto, new TypeReference<>() {});*/

        productMap.forEach((k, v) -> {
            if(v == null || v.equals("")){
                list.add(new ErrorDetails(HttpStatus.BAD_REQUEST.value(),k + " field must be send"));
            }else{
                map.forEach((key,value) ->{
                    if(k.equals(key)){
                        CheckNullField.checkIfIsNullWithoutRegExp(value,key,n -> n == null || n.equals(""), list);
                        if(value.getClass() != v.getClass()){
                            throw new BadRequestException("GSS-400-003",HttpStatus.BAD_REQUEST,key + " field must be a correct type value",new ArrayList<>());
                        }
                    }
                });
            }
        });

        product.setName(productUpdateDto.getName());
        product.setIdCategory(productUpdateDto.getCategoryId());
        product.setSellingPrice(productUpdateDto.getPrice());
        product.setStockQuantity(productUpdateDto.getStock());
        product.setState(productUpdateDto.isActive());
        product.setSupplierId(productUpdateDto.getIdSupplier());

        if(!list.isEmpty()){
            throw new BadRequestException("GSS-400-003",HttpStatus.BAD_REQUEST,"Field or fields cannot be null or an empty value",list);
        }
        productRepository.save(product);
        return productUpdateMapper.toProductUpdateDto(product);
    }

    private Product mapDtoToEntityUpdate(Product product, ProductDto productDto){
        product.setName(productDto.getName());
        product.setIdCategory(productDto.getCategoryId());
        product.setSellingPrice(productDto.getPrice());
        product.setStockQuantity(productDto.getStock());
        product.setState(productDto.isActive());
        //product.setSupplierId(productDto.getIdSupplier());
        product.setSupplierId(productDto.getSupplierDto().getSupplierId());
        return product;
    }

    private Product mapProductUpdateDtoToEntity(Product product, ProductUpdateDto productUpdateDto){
        product.setName(productUpdateDto.getName());
        product.setIdCategory(productUpdateDto.getCategoryId());
        product.setSellingPrice(productUpdateDto.getPrice());
        product.setStockQuantity(productUpdateDto.getStock());
        product.setState(productUpdateDto.isActive());
        product.setSupplierId(productUpdateDto.getIdSupplier());
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
