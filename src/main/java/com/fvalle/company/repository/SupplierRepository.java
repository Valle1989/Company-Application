package com.fvalle.company.repository;

import com.fvalle.company.entity.Order;
import com.fvalle.company.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Integer> {

    @Query(value = "SELECT * FROM suppliers WHERE city = :city", nativeQuery = true)
    List<Supplier> findSupplierByCity(@Param("city") String city);

    List<Supplier> findAllByNameContainingIgnoreCase(String name);
}
