package com.fvalle.company.controller;

import com.fvalle.company.dto.CustomerDto;
import com.fvalle.company.entity.Customer;
import com.fvalle.company.service.ICustomerService;
import com.fvalle.company.service.impl.CustomerServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {

    private final ICustomerService customerService;
    private final CustomerServiceImpl customerServiceImpl;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('user:create', 'admin:create')")
    public ResponseEntity<Customer> addCustomer(@Valid @RequestBody Customer customer){
        return new ResponseEntity<>(customerService.save(customer), HttpStatus.CREATED);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('user:create', 'admin:create')")
    public ResponseEntity<CustomerDto> addCustomerDto(@Valid @RequestBody CustomerDto customerDto){
        return new ResponseEntity<>(customerService.addCustomerDto(customerDto), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<Customer>> getAll(){
        return new ResponseEntity<>(customerService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/phone/{phone}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<Customer> getByPhone(@PathVariable("phone") String phone){
        return new ResponseEntity<>(customerServiceImpl.findByPhone(phone),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Integer id){
        return new ResponseEntity<>(customerService.getCustomerById(id),HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<Customer> updateCustomerByField(@PathVariable("id") Integer id, @RequestBody
    Map<String,Object> fields){
        return new ResponseEntity<>(customerService.updateCustomerByFields(id,fields), HttpStatus.OK);
    }
}
