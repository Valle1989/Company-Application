package com.fvalle.company.service.impl;

import com.fvalle.company.dto.EmployeeDto;
import com.fvalle.company.entity.Employee;
import com.fvalle.company.exception.BadRequestException;
import com.fvalle.company.exception.ErrorDetails;
import com.fvalle.company.exception.NotFoundException;
import com.fvalle.company.mapper.EmployeeMapper;
import com.fvalle.company.repository.EmployeeRepository;
import com.fvalle.company.security.repository.UserRepository;
import com.fvalle.company.service.IEmployeeService;
import com.fvalle.company.utils.CheckNullField;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

import static com.fvalle.company.utils.CheckNullField.*;


@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    private static final String BIRTHDATE_REGEXP = "^\\d{4}-\\d{2}-\\d{2}$";

    private static final String VALID_NAME = "^[A-Z]'?[- a-zA-Z]*$";

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
    public EmployeeDto saveEmployeeDto(EmployeeDto employeeDto) {
        Employee employee = employeeRepository.save(employeeMapper.toEmployee(employeeDto));
        return employeeMapper.toEmployeeDto(employee);
    }

    @Override
    public Employee update(Integer id, Employee employee) {

        Employee getEmployee = employeeRepository
                .findById(id).orElseThrow(() -> new NotFoundException("Product id not found"));
        List<ErrorDetails> list = new ArrayList<>();
        if(checkError(e -> e.getFirstName() == null || !isValid(e.getFirstName(),VALID_NAME), employee) ||
                checkError(e -> e.getLastName() == null || !isValid(e.getLastName(),VALID_NAME), employee) ||
                checkError(e -> e.getBirthDate() == null || !isValid(e.getBirthDate(),BIRTHDATE_REGEXP), employee) ||
                checkError(e -> e.getPhoto() == null || e.getPhoto().equals("") , employee) ||
                checkError(e -> e.getNotes() == null || e.getNotes().equals("") , employee)){

            checkIfIsNull(employee.getFirstName(),"FirstName",n -> n == null,VALID_NAME, list);
            checkIfIsNull(employee.getLastName(),"LastName",n -> n == null,VALID_NAME, list);
            checkIfIsNull(employee.getBirthDate(),"BirthDate",n -> n == null,BIRTHDATE_REGEXP, list);
            checkIfIsNullWithoutRegExp(employee.getPhoto(),"Photo",n -> n == null || n.equals(""), list);
            checkIfIsNullWithoutRegExp(employee.getNotes(),"Notes",n -> n == null || n.equals(""), list);
            throw new BadRequestException("GSS-400-003",HttpStatus.BAD_REQUEST,"All fields must be send",list);
        }
        getEmployee.setFirstName(employee.getFirstName());
        getEmployee.setLastName(employee.getLastName());
        getEmployee.setBirthDate(employee.getBirthDate());
        getEmployee.setPhoto(employee.getPhoto());
        getEmployee.setNotes(employee.getNotes());
        return employeeRepository.save(getEmployee);
    }

    @Override
    public Employee updateEmployeeByFields(Integer id, Map<String, Object> fields) {
        //String regExp = "^\\d{4}-\\d{2}-\\d{2}$";

        Employee getEmployee = employeeRepository
                .findById(id).orElseThrow(() -> new NotFoundException("Employee id not found"));

        List<Employee> employees = Arrays.asList(getEmployee);

        Map<String, Object> employeeMap = new HashMap<>();

        employees.stream()
                .forEach((employee) -> {
                    employeeMap.put("firstName", employee.getFirstName());
                    employeeMap.put("lastName", employee.getLastName());
                    employeeMap.put("birthDate", employee.getBirthDate());
                    employeeMap.put("photo", employee.getPhoto());
                    employeeMap.put("notes", employee.getNotes());
                });

        List<ErrorDetails> list = new ArrayList<>();
        fields.forEach((key,value) -> {
            if(value == null || value.equals("")){
                list.add(new ErrorDetails(HttpStatus.BAD_REQUEST.value(),key + " field must be send"));
            }else{
                employeeMap.forEach((k,v) ->{
                    if(k.equals(key)){
                        if(key.equals("birthDate") && !CheckNullField.isValid(value.toString(),BIRTHDATE_REGEXP)){
                            throw new BadRequestException("GSS-400-003",HttpStatus.BAD_REQUEST,key + " field must be a correct date value",new ArrayList<>());
                        }
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
