package com.fvalle.company.mapper;

import com.fvalle.company.dto.CustomerDto;
import com.fvalle.company.dto.EmployeeDto;
import com.fvalle.company.entity.Customer;
import com.fvalle.company.entity.Employee;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mappings({
            @Mapping(source = "firstName", target = "first_Name"),
            @Mapping(source = "lastName", target = "last_Name"),
            @Mapping(source = "birthDate", target = "employee_birthDate")
    })
    EmployeeDto toEmployeeDto(Employee employee);
    List<EmployeeDto> toEmployeesDto(List<Employee> employeeList);

    @InheritInverseConfiguration
    Employee toEmployee(EmployeeDto employeeDto);
}
