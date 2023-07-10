package com.fvalle.company.controller;

import com.fvalle.company.dto.EmployeeDto;
import com.fvalle.company.entity.Employee;
import com.fvalle.company.service.IEmployeeService;
import com.fvalle.company.service.impl.EmployeeServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
@AllArgsConstructor
public class EmployeeController {

    private final IEmployeeService employeeService;
    private final EmployeeServiceImpl employeeServiceImpl;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<Employee> addEmployee(@Valid @RequestBody Employee employee){
        return new ResponseEntity<>(employeeService.save(employee), HttpStatus.CREATED);
    }

    @PostMapping("/add/dto")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<EmployeeDto> addEmployeeDto(@Valid @RequestBody EmployeeDto employeeDto){
        return new ResponseEntity<>(employeeService.saveEmployeeDto(employeeDto), HttpStatus.CREATED);
    }

    @GetMapping("/byId/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<Employee> getById(@PathVariable("id") Integer id){
        return employeeService.getEmployee(id).
                map(employee -> new ResponseEntity<>(employee, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<Employee>> getAll(){
        return new ResponseEntity<>(employeeService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/all/page")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<Page<Employee>> getAllEmployee(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "8") int elements,
                                                         @RequestParam(defaultValue = "firstName") String sortBy,
                                                         @RequestParam(defaultValue = "ASC") String sortDirection){
        return new ResponseEntity<>(employeeServiceImpl.findAllEmployee(page,elements,sortBy,sortDirection),
                                    HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Integer id,
                                                    @Valid @RequestBody Employee employee){
        return new ResponseEntity<>(employeeService.update(id,employee), HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<Employee> updateEmployeeByField(@PathVariable("id") Integer id, @RequestBody
                                                   Map<String,Object> fields){
        return new ResponseEntity<>(employeeService.updateEmployeeByFields(id,fields), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<String> delete(@Valid @PathVariable("id") Integer id) {
        if(employeeService.delete(id)){
            return new ResponseEntity<>("Employee with id " + id + " deleted",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Id not found",HttpStatus.NOT_FOUND);
        }
    }
}
