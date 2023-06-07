package com.fvalle.company.service.impl;

import com.fvalle.company.dto.CustomerDto;
import com.fvalle.company.entity.Customer;
import com.fvalle.company.exception.BadRequestException;
import com.fvalle.company.exception.NotFoundException;
import com.fvalle.company.mapper.CustomerMapper;
import com.fvalle.company.repository.CustomerRepository;
import com.fvalle.company.service.ICustomerService;
import com.fvalle.company.utils.GenericMethods;
import com.fvalle.company.utils.UpdateEntityByFields;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService, GenericMethods<Customer> {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final UpdateEntityByFields<Customer> updateEntityByFields;


    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<CustomerDto> findAll() {
        return customerMapper.toCustomersDto(customerRepository.findAll());
    }

    @Override
    public Optional<CustomerDto> getCustomerDto(int id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isPresent()){
            return customer.map(customerMapper::toCustomerDto);
        }else{
            throw new NotFoundException("Id cannot be found");
        }
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public CustomerDto addCustomerDto(CustomerDto customerDto) {
        Customer customer = customerMapper.toCustomer(customerDto);
        return customerMapper.toCustomerDto(customerRepository.save(customer));
    }

    @Override
    public Customer updateCustomer(Integer id, Customer customer) {
        Customer customerToUpdate = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Id, not found"));
        customerToUpdate.setName(customer.getName());
        customerToUpdate.setAddress(customer.getAddress());
        customerToUpdate.setCity(customer.getCity());
        customerToUpdate.setPostalCode(customer.getPostalCode());
        customerToUpdate.setCountry(customer.getCountry());
        customerToUpdate.setPhone(customer.getPhone());
        return customerRepository.save(customerToUpdate);
    }

    @Override
    public Customer updateCustomerByFields(Integer id, Map<String, Object> fields){
        Customer customer = updateEntityByFields.updateFields(id,fields,customerRepository);
        return customer;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Customer updateEntityByField(Integer id, Map<String, Object> fields) {
        return null;
    }
}
