package com.fvalle.company.controller;

import com.fvalle.company.dto.CategoryDto;
import com.fvalle.company.dto.PurchaseOrderDto;
import com.fvalle.company.entity.Order;
import com.fvalle.company.projection.OrderSummary;
import com.fvalle.company.service.IOrderService;
import com.fvalle.company.service.impl.OrderServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    private final IOrderService orderService;
    private final OrderServiceImpl orderServiceImpl;

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('user:create', 'admin:create')")
    public ResponseEntity<Order> addOrder(@Valid @RequestBody Order order){
        return new ResponseEntity<>(orderService.save(order), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<PurchaseOrderDto>> getAll(){
        return new ResponseEntity<>(orderService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/customer/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<Order>> getOutsideOrders(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(orderServiceImpl.getCustomerOrders(id));
    }

    @GetMapping("/summary/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<OrderSummary> getSummary(@PathVariable int id) {
        return ResponseEntity.ok(orderServiceImpl.getSummary(id));
    }

    @GetMapping("/allOrder")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<Order>> getAllOrders(){
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<?> delete(@Valid @PathVariable("id") Integer id) {
        return orderService.delete(id);
    }
}
