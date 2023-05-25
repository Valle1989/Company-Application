package com.fvalle.company.service.impl;

import com.fvalle.company.entity.Employee;
import com.fvalle.company.entity.Product;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        fields.forEach((key,value) -> {
            Field field = ReflectionUtils.findField(Employee.class,key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, getEmployee, value);
        });
        return employeeRepository.save(getEmployee);
    }

    /*@Override
    public ResponseEntity<?> delete(Integer id) {
        UserDetails activeUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = activeUser.getUsername();
        User user = userRepository.findByEmail(username).get();

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee id not found"));

        if (employee.getId() == user.getId()) {
            employeeRepository.delete(employee);
            return new ResponseEntity<>("Employee deleted", HttpStatus.OK);
        } else {
            throw new BadCredentialsException("Error, bad credentials.");
        }
    }*/
}
