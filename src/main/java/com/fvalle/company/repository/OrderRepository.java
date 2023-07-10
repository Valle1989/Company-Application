package com.fvalle.company.repository;

import com.fvalle.company.entity.Order;
import com.fvalle.company.projection.OrderSummary;
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

    @Query(value =
            "SELECT ord.id_order as idOrder, cu.name as customerName, em.first_name as employeeFirstName, " +
                        "em.last_name as employeeLastName, shi.name as shipperName, shi.phone as shipperPhone, " +
                        "ord.order_date as orderDate " +
                    "from orders ord " +
                        "inner join customers cu on ord.id_customer = cu.id " +
                        "inner join employees em on ord.id_employee = em.id " +
                        "inner join shippers shi on ord.id_shipper = shi.id " +
                    "where ord.id_order = :orderId", nativeQuery = true)
    OrderSummary findSummary(@Param("orderId") int orderId);
}
