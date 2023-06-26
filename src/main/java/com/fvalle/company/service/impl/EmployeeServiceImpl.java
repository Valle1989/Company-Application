package com.fvalle.company.service.impl;

import com.fvalle.company.dto.EmployeeDto;
import com.fvalle.company.entity.Employee;
import com.fvalle.company.exception.BadRequestException;
import com.fvalle.company.exception.ErrorDetails;
import com.fvalle.company.exception.NotFoundException;
import com.fvalle.company.mapper.EmployeeMapper;
import com.fvalle.company.repository.EmployeeRepository;
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
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private static final String BIRTHDATE_REGEXP = "^\\d{4}-\\d{2}-\\d{2}$";
    private static final String VALID_NAME = "^[A-Z]'?[- a-zA-Z]*$";

    /**
     * Method used to obtain the list of Employees
     * @return List<Employee> list
     */
    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    /**
     * Method used to get a employee by id
     * @param employeeId
     * @return Employee
     * @throws NotFoundException if the id could not be found
     */
    @Override
    public Optional<Employee> getEmployee(int employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if(!employee.isPresent()){
            throw new NotFoundException("Error, id cannot be found");
        }else{
            return employee;
        }
    }

    /**
     * Method used to save an employee to the database
     * @param employee
     * @return Employee
     */
    @Override
    public Employee save(Employee employee) {
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
        return employeeRepository.save(employee);
    }

    /**
     * Method used to receive an employeeDto as a parameter and then to use it to convert the employeeDto to
     * an employee to can save it into the database. However, our service will return an EmployeeDto.
     * @param employeeDto
     * @return EmployeeDto
     */
    @Override
    public EmployeeDto saveEmployeeDto(EmployeeDto employeeDto) {
        List<ErrorDetails> list = new ArrayList<>();
        if(checkError(e -> e.getFirst_Name() == null || !isValid(e.getFirst_Name(),VALID_NAME), employeeDto) ||
                checkError(e -> e.getLast_Name() == null || !isValid(e.getLast_Name(),VALID_NAME), employeeDto) ||
                checkError(e -> e.getEmployee_birthDate() == null || !isValid(e.getEmployee_birthDate(),BIRTHDATE_REGEXP), employeeDto) ||
                checkError(e -> e.getEmployee_photo() == null || e.getEmployee_photo().equals("") , employeeDto) ||
                checkError(e -> e.getEmployee_notes() == null || e.getEmployee_notes().equals("") , employeeDto)){

            checkIfIsNull(employeeDto.getFirst_Name(),"first_Name",n -> n == null,VALID_NAME, list);
            checkIfIsNull(employeeDto.getLast_Name(),"last_Name",n -> n == null,VALID_NAME, list);
            checkIfIsNull(employeeDto.getEmployee_birthDate(),"employee_birthDate",n -> n == null,BIRTHDATE_REGEXP, list);
            checkIfIsNullWithoutRegExp(employeeDto.getEmployee_photo(),"employee_photo",n -> n == null || n.equals(""), list);
            checkIfIsNullWithoutRegExp(employeeDto.getEmployee_notes(),"employee_notes",n -> n == null || n.equals(""), list);
            throw new BadRequestException("GSS-400-003",HttpStatus.BAD_REQUEST,"All fields must be send",list);
        }
        Employee employee = employeeRepository.save(employeeMapper.toEmployee(employeeDto));
        return employeeMapper.toEmployeeDto(employee);
    }

    /**
     * Method used to update an employee into the database.
     * @param id
     * @param employee
     * @throws NotFoundException if the id could not be found
     * @return Employee
     */
    @Override
    public Employee update(Integer id, Employee employee) {
        Optional<Employee> em = employeeRepository.findById(id);
        if(!em.isPresent()){
            throw new NotFoundException("Error, id cannot be found");
        }else{
            Employee getEmployee = em.get();
            //Employee getEmployee = employeeRepository
            //.findById(id).orElseThrow(() -> new NotFoundException("Employee id not found"));
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
        /*Employee getEmployee = em.get();
        //Employee getEmployee = employeeRepository
                //.findById(id).orElseThrow(() -> new NotFoundException("Employee id not found"));
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
        return employeeRepository.save(getEmployee);*/
    }

    /**
     * Method used to partially update an employee, without the need to send all its fields.
     * @param id
     * @param fields
     * @return Employee
     */
    @Override
    public Employee updateEmployeeByFields(Integer id, Map<String, Object> fields) {

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
                    /*if(v.equals(value)){
                        if(!k.equals(key)) throw new BadRequestException("GSS-400-003",HttpStatus.BAD_REQUEST,key + " field misspelled",new ArrayList<>());
                    }*/
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

    /**
     * Method used to delete an employee from de database
     * @return true or false
     */
    @Override
    public boolean delete(Integer id) {
        if (getEmployee(id).isPresent()){
            employeeRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

}
