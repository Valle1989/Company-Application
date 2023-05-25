package com.fvalle.company.mapper;

import com.fvalle.company.dto.CategoryDto;
import com.fvalle.company.dto.CustomerDto;
import com.fvalle.company.entity.Category;
import com.fvalle.company.entity.Customer;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mappings({
            @Mapping(source = "name", target = "customerName"),
            @Mapping(source = "address", target = "customerAddress"),
            @Mapping(source = "city", target = "customerCity"),
            @Mapping(source = "phone", target = "customerPhone")
    })
    CustomerDto toCustomerDto(Customer customer);
    List<CustomerDto> toCustomersDto(List<Customer> customerList);

    @InheritInverseConfiguration
    Customer toCustomer(CustomerDto customerDto);
}
