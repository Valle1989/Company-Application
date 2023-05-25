package com.fvalle.company.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "firstName field cannot be null")
    private String firstName;
    @NotNull(message = "lastName field cannot be null")
    private String lastName;
    @NotNull(message = "birthDate field cannot be null")
    private LocalDate birthDate;
    @NotNull(message = "photo field cannot be null")
    private String photo;
    @NotNull(message = "notes field cannot be null")
    private String notes;

    @OneToMany(mappedBy = "employee")
    private List<Order> orders;
}
