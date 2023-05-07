package com.fvalle.company.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category")
    private Integer id;

    @NotNull(message = "description field cannot be null")
    private String description;

    @NotNull(message = "state field cannot be null")
    private Boolean state;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
