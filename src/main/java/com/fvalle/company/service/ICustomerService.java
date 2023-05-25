package com.fvalle.company.service;

import com.fvalle.company.entity.Customer;
import com.fvalle.company.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {

    List<Customer> getAll();
    Optional<Employee> getCustomer(int id);
    Customer save(Customer customer);
}
