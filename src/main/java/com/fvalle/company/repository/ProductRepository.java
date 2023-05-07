package com.fvalle.company.repository;

import com.fvalle.company.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    List<Product> findByIdCategoryOrderByNameAsc(int idCategory);
    Optional<List<Product>> findByStockQuantityLessThanAndState(int stockQuantity, boolean state);
}
