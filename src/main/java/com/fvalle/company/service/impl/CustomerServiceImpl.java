package com.fvalle.company.service.impl;

import com.fvalle.company.entity.Customer;
import com.fvalle.company.entity.Employee;
import com.fvalle.company.repository.CustomerRepository;
import com.fvalle.company.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Employee> getCustomer(int id) {
        return Optional.empty();
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }
}
