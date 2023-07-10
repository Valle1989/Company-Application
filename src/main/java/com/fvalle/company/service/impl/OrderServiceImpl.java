package com.fvalle.company.service.impl;

import com.fvalle.company.dto.PurchaseOrderDto;
import com.fvalle.company.entity.Order;
import com.fvalle.company.exception.NotFoundException;
import com.fvalle.company.mapper.CustomerMapper;
import com.fvalle.company.mapper.EmployeeMapper;
import com.fvalle.company.mapper.ShipperMapper;
import com.fvalle.company.repository.OrderRepository;
import com.fvalle.company.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository orderRepository;
    private final CustomerMapper customerMapper;
    private final EmployeeMapper employeeMapper;

    private final ShipperMapper shipperMapper;

    @Override
    public List<PurchaseOrderDto> getAll() {
        return orderRepository.findAll()
        .stream().map(order -> new PurchaseOrderDto(
                order.getIdOrder(),
                customerMapper.toCustomerDto(order.getCustomer()),
                employeeMapper.toEmployeeDto(order.getEmployee()),
                shipperMapper.toShipperDto(order.getShipper())
        )).collect(Collectors.toList());
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getCustomerOrders(Integer idCustomer) {
        return this.orderRepository.findCustomerOrders(idCustomer);
    }

    @Override
    @Transactional
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public ResponseEntity<?> delete(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order id not found"));

        if (order != null && order.getUser().getUsername().equals(username)) {
            // The authenticated user has permission to delete the order
            orderRepository.delete(order);
            return new ResponseEntity<>("Order deleted", HttpStatus.OK);
        } else {
            // The user does not have permission to delete the order
            throw new BadCredentialsException("Error, bad credentials.");
        }
    }
}
