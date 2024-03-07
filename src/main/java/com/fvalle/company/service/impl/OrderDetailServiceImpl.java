package com.fvalle.company.service.impl;

import com.fvalle.company.entity.OrderDetail;
import com.fvalle.company.repository.OrderDetailRepository;
import com.fvalle.company.service.IOrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements IOrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> getAll() {
        return orderDetailRepository.findAll();
    }

    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public ResponseEntity<?> delete(Integer id) {
        return null;
    }
}
