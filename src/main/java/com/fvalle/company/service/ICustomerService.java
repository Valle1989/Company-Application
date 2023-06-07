package com.fvalle.company.service;

import com.fvalle.company.dto.CustomerDto;
import com.fvalle.company.entity.Customer;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ICustomerService {

    List<Customer> getAll();

    List<CustomerDto> findAll();
    Optional<CustomerDto> getCustomerDto(int id);
    Customer save(Customer customer);

    CustomerDto addCustomerDto(CustomerDto customerDto);

    Customer updateCustomer(Integer id, Customer customer);

    Customer updateCustomerByFields(Integer id, Map<String,Object> fields);

    public boolean delete (Integer id);
}
