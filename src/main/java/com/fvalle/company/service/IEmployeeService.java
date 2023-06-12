package com.fvalle.company.service;

import com.fvalle.company.dto.EmployeeDto;
import com.fvalle.company.dto.ProductDto;
import com.fvalle.company.entity.Employee;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IEmployeeService {

    List<Employee> getAll();
    Optional<Employee> getEmployee(int productId);
    Employee save(Employee employee);

    EmployeeDto saveEmployeeDto(EmployeeDto employeeDto);

    Employee update(Integer id, Employee employee);

    Employee updateEmployeeByFields(Integer id, Map<String,Object> fields);

    //ResponseEntity<?> delete(Integer id);
}
