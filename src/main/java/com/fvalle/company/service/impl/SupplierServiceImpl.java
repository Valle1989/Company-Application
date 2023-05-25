package com.fvalle.company.service.impl;

import com.fvalle.company.dto.ProductDto;
import com.fvalle.company.dto.SupplierWithProductDto;
import com.fvalle.company.entity.Product;
import com.fvalle.company.entity.Supplier;
import com.fvalle.company.mapper.ProductMapper;
import com.fvalle.company.mapper.SupplierWithProductMapper;
import com.fvalle.company.repository.ProductRepository;
import com.fvalle.company.repository.SupplierRepository;
import com.fvalle.company.service.ISupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements ISupplierService {

    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final SupplierWithProductMapper supplierWithProductMapper;
    private final ProductMapper productMapper;

    @Override
    public List<SupplierWithProductDto> getAll() {
        List<Supplier> list = supplierRepository.findAll();
        return supplierWithProductMapper.toSuppliersDto(list);
    }

    @Override
    public Supplier add(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public SupplierWithProductDto save(SupplierWithProductDto supplierWithProductDto) {
        Supplier supplier = supplierWithProductMapper.toSupplier(supplierWithProductDto);
        supplierRepository.save(supplier);
        List<ProductDto> products = supplierWithProductDto.getSupplierProducts();
        products.stream()
                .map(p -> productMapper.toProduct(p))
                .forEach(p -> productRepository.save(p));
        /*List<Product> list = new ArrayList<>();
        for(ProductDto pro : products){
            Product product = productMapper.toProduct(pro);
            list.add(product);
        }
        supplier.setProducts(list);*/
        return supplierWithProductMapper.toSupplierDto(supplier);
    }

    @Override
    public boolean delete(int supplierId) {
        return false;
    }
}
