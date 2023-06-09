package com.fvalle.company.service.impl;

import com.fvalle.company.entity.Employee;
import com.fvalle.company.entity.Product;
import com.fvalle.company.exception.BadRequestException;
import com.fvalle.company.exception.ErrorDetails;
import com.fvalle.company.exception.NotFoundException;
import com.fvalle.company.repository.EmployeeRepository;
import com.fvalle.company.security.repository.UserRepository;
import com.fvalle.company.security.user.User;
import com.fvalle.company.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployee(int productId) {
        return Optional.empty();
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Integer id, Employee employee) {
        Employee getEmployee = employeeRepository
                .findById(id).orElseThrow(() -> new NotFoundException("Product id not found"));
        getEmployee.setFirstName(employee.getFirstName());
        getEmployee.setLastName(employee.getLastName());
        getEmployee.setBirthDate(employee.getBirthDate());
        getEmployee.setPhoto(employee.getPhoto());
        getEmployee.setNotes(employee.getNotes());
        return employeeRepository.save(getEmployee);
    }

    @Override
    public Employee updateEmployeeByFields(Integer id, Map<String, Object> fields) {
        Employee getEmployee = employeeRepository
                .findById(id).orElseThrow(() -> new NotFoundException("Employee id not found"));

        List<Employee> employees = new ArrayList<>();
        employees.add(getEmployee);

        Map<String, Object> employeeMap = new HashMap<>();
        for(Employee employee : employees){
            employeeMap.put("firstName", employee.getFirstName());
            employeeMap.put("lastName", employee.getLastName());
            employeeMap.put("birthDate", employee.getBirthDate());
            employeeMap.put("photo", employee.getPhoto());
            employeeMap.put("notes", employee.getNotes());
        }

        List<ErrorDetails> list = new ArrayList<>();
        fields.forEach((key,value) -> {
            if(value == null || value.equals("")){
                list.add(new ErrorDetails(HttpStatus.BAD_REQUEST.value(),key + " field must be send"));
            }else{
                employeeMap.forEach((k,v) ->{
                    if(k.equals(key)){
                        if(v.getClass() != value.getClass()){
                            throw new BadRequestException("GSS-400-003",HttpStatus.BAD_REQUEST,key + " field must be a correct type value",new ArrayList<>());
                        }
                    }
                });
            }
            Field field = ReflectionUtils.findField(Employee.class,key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, getEmployee, value);
        });
        if(!list.isEmpty()){
            throw new BadRequestException("GSS-400-003",HttpStatus.BAD_REQUEST,"Field or fields cannot be null or an empty value",list);
        }
        return employeeRepository.save(getEmployee);
    }
    
}
