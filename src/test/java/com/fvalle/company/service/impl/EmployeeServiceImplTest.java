package com.fvalle.company.service.impl;

import com.fvalle.company.dto.EmployeeDto;
import com.fvalle.company.entity.Employee;
import com.fvalle.company.exception.BadRequestException;
import com.fvalle.company.exception.ErrorDetails;
import com.fvalle.company.exception.NotFoundException;
import com.fvalle.company.mapper.EmployeeMapper;
import com.fvalle.company.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    //@Mock
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    private EmployeeDto employeeDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        //employeeRepository = Mockito.mock(EmployeeRepository.class);
        //employeeService = new EmployeeServiceImpl(employeeRepository,employeeMapper);

        employee = new Employee();
        employee.setId(1);
        employee.setFirstName("Federico");
        employee.setLastName("Valle");
        employee.setBirthDate("1989-12-27");
        employee.setPhoto("My photo");
        employee.setNotes("New message");

        employeeDto = new EmployeeDto();
        employeeDto.setFirst_Name("Melisa");
        employeeDto.setLast_Name("Lieby");
        employeeDto.setEmployee_birthDate("1991-08-03");
        employeeDto.setEmployee_photo("Meli photo");
        employeeDto.setEmployee_notes("Ux/Ui Designer");
    }

    @Test
    void getAll() {
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee));
        Assertions.assertNotNull(employeeService.getAll());
    }

    @Test
    void getEmployee() {
        Employee waited = new Employee();
        waited.setId(1);
        waited.setFirstName("Federico");
        waited.setLastName("Valle");
        waited.setBirthDate("1989-12-27");
        waited.setPhoto("My photo");
        waited.setNotes("New message");

        Optional<Employee> optionalEntity = Optional.of(employee);
        Optional<Employee> optionalWaited = Optional.of(waited);
        /*Mockito.when(employeeRepository.findById(1)).thenReturn(optionalEntity);

        Optional<Employee> result = employeeRepository.findById(1);

        assertEquals(optionalEntity,result);
        verify(employeeRepository).findById(1);*/
        when(employeeService.save(waited)).
                thenReturn(waited);

        Employee result = employeeService.save(waited);

        when(employeeService.getEmployee(1)).thenReturn(Optional.of(result));

        Optional<Employee> res = employeeService.getEmployee(1);
        assertEquals(employee,res);
    }

    @Test
    void saveOK() {
        Employee waited = new Employee();
        waited.setId(1);
        waited.setFirstName("Federico");
        waited.setLastName("Valle");
        waited.setBirthDate("1989-12-27");
        waited.setPhoto("My photo");
        waited.setNotes("New message");

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        Employee employeeSaved = employeeRepository.save(employee);
        when(employeeService.save(employee)).
                thenReturn(employee);

        Employee result = employeeService.save(employee);

        assertEquals(waited.getId(),result.getId());
        assertEquals(waited.getFirstName(),result.getFirstName());
        assertEquals(waited.getLastName(),result.getLastName());
        assertEquals(waited.getBirthDate(),result.getBirthDate());
        assertEquals(waited.getPhoto(),result.getPhoto());
        assertEquals(waited.getNotes(),result.getNotes());
        assertEquals(waited,result);

        verify(employeeRepository).save(employee);
    }

    @Test
    public void saveEmployeeWithErrorFieldNameEmptyOrNull(){
        Employee waited = new Employee();
        waited.setId(1);
        waited.setFirstName("");
        waited.setLastName("Valle");
        waited.setBirthDate("1989-12-27");
        waited.setPhoto("My photo");
        waited.setNotes("New message");

        assertThrows(BadRequestException.class, () -> {
            employeeService.save(waited);
        });
    }

    @Test
    void saveEmployeeDtoOk() {
        EmployeeDto waited = new EmployeeDto();
        waited.setFirst_Name("Melisa");
        waited.setLast_Name("Lieby");
        waited.setEmployee_birthDate("1991-08-03");
        waited.setEmployee_photo("Meli photo");
        waited.setEmployee_notes("Ux/Ui Designer");

        when(employeeService.saveEmployeeDto(employeeDto)).
                thenReturn(employeeDto);

        EmployeeDto result = employeeService.saveEmployeeDto(employeeDto);

        assertEquals(waited.getFirst_Name(),result.getFirst_Name());
        assertEquals(waited.getLast_Name(),result.getLast_Name());
        assertEquals(waited.getEmployee_birthDate(),result.getEmployee_birthDate());
        assertEquals(waited.getEmployee_photo(),result.getEmployee_photo());
        assertEquals(waited.getEmployee_notes(),result.getEmployee_notes());
        assertEquals(waited,result);
    }

    @Test
    void updateOK() {
        Integer id = 1;
        Employee waited = new Employee();
        waited.setId(1);
        waited.setFirstName("Federico");
        waited.setLastName("Valle");
        waited.setBirthDate("1989-12-27");
        waited.setPhoto("My photo");
        waited.setNotes("New message");

        when(employeeService.save(employee)).
                thenReturn(employee);

        Employee result = employeeService.save(employee);

        when(employeeService.update(id,employee)).
                thenReturn(employee);

        /*Optional<Employee> optionalEntity = Optional.of(employee);
        Optional<Employee> optionalWaited = Optional.of(waited);
        Mockito.when(employeeRepository.findById(1)).thenReturn(optionalEntity);

        Optional<Employee> result = employeeRepository.findById(1);
        when(employeeRepository.save(result.get())).thenReturn(result.get());*/

        /*when(employeeRepository.findById(id)).thenReturn(Optional.empty());
        when(employeeRepository.save(waited)).thenReturn(waited);

        //when(employeeService.update(id,employee)).thenReturn(employee);*/

        Employee result2 = employeeService.update(id,employee);

        assertEquals(waited.getId(),result2.getId());
        assertEquals(waited.getFirstName(),result2.getFirstName());
        assertEquals(waited.getLastName(),result2.getLastName());
        assertEquals(waited.getBirthDate(),result2.getBirthDate());
        assertEquals(waited.getPhoto(),result2.getPhoto());
        assertEquals(waited.getNotes(),result2.getNotes());
        assertEquals(waited, result2);
    }

    @Test
    public void testUpdateEmployee() {
        // Datos de prueba
        Integer employeeId = 1;
        Employee existingEmployee = new Employee();
        existingEmployee.setId(employeeId);
        existingEmployee.setFirstName("John");
        existingEmployee.setLastName("Doe");
        existingEmployee.setBirthDate("1990-01-01");
        existingEmployee.setPhoto("photo.jpg");
        existingEmployee.setNotes("Some notes");

        Employee updatedEmployee = new Employee();
        updatedEmployee.setFirstName("Jane");
        updatedEmployee.setLastName("Smith");
        updatedEmployee.setBirthDate("1995-02-02");
        updatedEmployee.setPhoto("new_photo.jpg");
        updatedEmployee.setNotes("Updated notes");

        given(employeeRepository.findById(1)).willReturn(Optional.of(employee));
        given(employeeRepository.save(any(Employee.class))).willReturn(employee);

        Employee result = employeeService.update(1, updatedEmployee);

        verify(employeeRepository).save(any(Employee.class));
        assertThat(result).isExactlyInstanceOf(result.getClass());

        /*// Mockear el repositorio para devolver el empleado existente
        //when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        Mockito.when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        Employee employeeSaved = employeeRepository.save(employee);
        //when(employeeService.getEmployee(employeeId)).thenReturn(Optional.of(employee));
        //when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employeeSaved));
        Optional<Employee> getEmployee = employeeRepository.findById(1);

        // Llamar al método que se va a probar
        Employee result = employeeService.update(getEmployee.get().getId(), updatedEmployee);

        // Verificar que el método findById del repositorio haya sido llamado
        verify(employeeRepository).findById(employeeId);

        // Verificar que el método save del repositorio haya sido llamado con el empleado actualizado
        ArgumentCaptor<Employee> argumentCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(argumentCaptor.capture());
        Employee savedEmployee = argumentCaptor.getValue();

        // Realizar las aserciones para verificar que los datos se actualizaron correctamente
        assertEquals(updatedEmployee.getFirstName(), savedEmployee.getFirstName());
        assertEquals(updatedEmployee.getLastName(), savedEmployee.getLastName());
        assertEquals(updatedEmployee.getBirthDate(), savedEmployee.getBirthDate());
        assertEquals(updatedEmployee.getPhoto(), savedEmployee.getPhoto());
        assertEquals(updatedEmployee.getNotes(), savedEmployee.getNotes());

        // Verificar que el objeto retornado es el empleado actualizado
        assertEquals(savedEmployee, result);*/


    }

    @Test
    public void testUpdateNonExistingEmployee() {
        // Mocked employee data
        Integer employeeId = 1;

        // Updated employee data
        Employee updatedEmployee = new Employee();
        updatedEmployee.setFirstName("Jane");
        updatedEmployee.setLastName("Smith");
        updatedEmployee.setBirthDate("1995-02-02");
        updatedEmployee.setPhoto("new_photo.jpg");
        updatedEmployee.setNotes("Updated notes");

        // Configure the mock repository to return an empty Optional
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        Optional<Employee> emp = employeeRepository.findById(employeeId);

        // Call the update method and assert that it throws the expected exception
        assertThrows(NotFoundException.class, () -> employeeService.update(employeeId, updatedEmployee));

        // Verify that the findById method was called on the mock repository
        verify(employeeRepository).findById(employeeId);

        // Verify that the save method was not called on the mock repository
        //verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void updateEmployeeByFields() {
    }

    @Test
    void delete() {
        /*Employee waited = new Employee();
        waited.setId(1);
        waited.setFirstName("Federico");
        waited.setLastName("Valle");
        waited.setBirthDate("1989-12-27");
        waited.setPhoto("My photo");
        waited.setNotes("New message");

        Optional<Employee> optionalEntity = Optional.of(employee);
        Optional<Employee> optionalWaited = Optional.of(waited);
        Mockito.when(employeeRepository.findById(1)).thenReturn(optionalEntity);

        Optional<Employee> result = employeeRepository.findById(1);*/

        when(employeeRepository.existsById(1)).thenReturn(false);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> employeeService.delete(1));
        //assertTrue(employeeService.delete(1));

        /*employeeRepository.deleteById(1);
        verify(employeeRepository).deleteById(1);*/

        /*Mockito.when(employeeRepository.deleteById(1)).
                thenReturn(true);
        Boolean deleted = employeeService.delete(result.get().getId());
        Assertions.assertTrue(deleted);*/

        /*Assertions.assertThrows(NotFoundException.class, () -> {
            employeeService.delete(1);
        });*/

        //employeeService.delete(1);
        //verify(employeeRepository).deleteById(1);
    }
}