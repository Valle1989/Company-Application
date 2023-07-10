package com.fvalle.company.repository;

import com.fvalle.company.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    //Query SQL con código nativo
    @Query(value = "SELECT * FROM orders WHERE id_customer = :id", nativeQuery = true)
    List<Order> findCustomerOrders(@Param("id") Integer idCustomer);
}
