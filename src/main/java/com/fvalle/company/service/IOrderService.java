package com.fvalle.company.service;

import com.fvalle.company.dto.PurchaseOrderDto;
import com.fvalle.company.entity.Order;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IOrderService {

    List<PurchaseOrderDto> getAll();
    List<Order> getAllOrders();
    Order save(Order order);
    ResponseEntity<?> delete(Integer id);
}
