package com.fvalle.company.service;

import com.fvalle.company.entity.OrderDetail;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IOrderDetailService {

    List<OrderDetail> getAll();
    OrderDetail save(OrderDetail orderDetail);
    ResponseEntity<?> delete(Integer id);
}
