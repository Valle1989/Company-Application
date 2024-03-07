package com.fvalle.company.controller;

import com.fvalle.company.entity.OrderDetail;
import com.fvalle.company.service.IOrderDetailService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order/detail")
@AllArgsConstructor
public class OrderDetailController {

    private final IOrderDetailService orderDetailService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<OrderDetail>> getAll(){
        return new ResponseEntity<>(orderDetailService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('user:create', 'admin:create')")
    public ResponseEntity<OrderDetail> addOrderDetail(@Valid @RequestBody OrderDetail orderDetail){
        return new ResponseEntity<>(orderDetailService.save(orderDetail), HttpStatus.CREATED);
    }
}
