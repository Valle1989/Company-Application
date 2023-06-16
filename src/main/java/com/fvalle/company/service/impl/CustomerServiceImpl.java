package com.fvalle.company.service.impl;

import com.fvalle.company.dto.CustomerDto;
import com.fvalle.company.entity.Customer;
import com.fvalle.company.exception.NotFoundException;
import com.fvalle.company.mapper.CustomerMapper;
import com.fvalle.company.repository.CustomerRepository;
import com.fvalle.company.service.ICustomerService;
import com.fvalle.company.utils.UpdateEntityByFields;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService{

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final UpdateEntityByFields<Customer,String> updateEntityByFields;

    /**
     * Method used to obtain the list of Customers
     * @return List<Customer> list
     */
    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    /**
     * Method used to obtain the list of CustomersDto
     * @return List<CustomerDto> list
     */
    @Override
    public List<CustomerDto> findAll() {
        return customerMapper.toCustomersDto(customerRepository.findAll());
    }

    /**
     * Method used to obtain the list of CustomersDto
     * @param id
     * @return CustomerDto
     * @throws NotFoundException if the id could not be found
     */
    @Override
    public Optional<CustomerDto> getCustomerDto(int id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isPresent()){
            return customer.map(customerMapper::toCustomerDto);
        }else{
            throw new NotFoundException("Id cannot be found");
        }
    }

    /**
     * Method used to get a customer by id
     * @param id
     * @return Customer
     * @throws NotFoundException if the id could not be found
     */
    @Override
    public Customer getCustomerById(Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Id, not found"));
        return customer;
    }

    /**
     * Method used to save a customer to the database
     * @param customer
     * @return Customer
     */
    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    /**
     * Method used to receive a customerDto as a parameter and then to use it to convert the customerDto to
     * a customer to can save it into the database. However, our method will return a CustomerDto.
     * @param customerDto
     * @return CustomerDto
     */
    @Override
    public CustomerDto addCustomerDto(CustomerDto customerDto) {
        Customer customer = customerMapper.toCustomer(customerDto);
        return customerMapper.toCustomerDto(customerRepository.save(customer));
    }

    /**
     * Method used to update a customer into the database.
     * @param id
     * @param customer
     * @throws NotFoundException if the id could not be found
     * @return Customer
     */
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

    /**
     * Method used to partially update a client, without the need to send all its fields.
     * @param id
     * @param fields
     * @return Customer
     */
    @Override
    public Customer updateCustomerByFields(Integer id, Map<String, Object> fields){
        Customer customer = updateEntityByFields.updateFields(id,fields,customerRepository,"String");
        return customer;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

}
