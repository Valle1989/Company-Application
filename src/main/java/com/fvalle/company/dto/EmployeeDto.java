package com.fvalle.company.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    @NotEmpty(message = "first_Name cannot be null or empty")
    private String first_Name;
    @NotEmpty(message = "last_name cannot be null or empty")
    private String last_Name;
    @NotEmpty(message = "employee_birthDate cannot be null or empty")
    private String  employee_birthDate;
    @NotEmpty(message = "employee_photo cannot be null or empty")
    private String  employee_photo;
    @NotEmpty(message = "employee_notes cannot be null or empty")
    private String  employee_notes;
}
