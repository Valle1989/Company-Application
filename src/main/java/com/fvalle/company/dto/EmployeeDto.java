package com.fvalle.company.dto;

import com.fvalle.company.utils.ImageExtension;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    @NotNull(message = "first_Name field cannot be null")
    private String first_Name;
    @NotNull(message = "last_Name field cannot be null")
    private String last_Name;
    @NotNull(message = "birth_Date field cannot be null")
    private String  employee_birthDate;
    @URL(message = "image must be an URL")
    @ImageExtension(message = "image extension not valid, must be JPG, JPEG or PNG")
    private String  employee_photo;
    @Size(message = "The message can only contain a minimum of 5 characters and a maximum of 50 characters.", min = 5,max = 100)
    private String  employee_notes;
}
