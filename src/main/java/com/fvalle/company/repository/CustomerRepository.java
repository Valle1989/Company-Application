package com.fvalle.company.repository;

import com.fvalle.company.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    //Query con JPQL usando la anotación @Query
    @Query(value = "SELECT c FROM Customer c WHERE c.phone = :phone")
    Customer findByPhone(@Param("phone") String phone);
}
