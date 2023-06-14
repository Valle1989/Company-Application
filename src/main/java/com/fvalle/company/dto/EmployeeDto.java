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

    private String first_Name;
    private String last_Name;
    private String  employee_birthDate;
    private String  employee_photo;
    private String  employee_notes;
}
