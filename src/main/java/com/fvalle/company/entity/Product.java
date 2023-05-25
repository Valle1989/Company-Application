package com.fvalle.company.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProduct;

    @NotNull(message = "Name field cannot be null")
    private String name;
    @Column(name = "id_category")
    @NotNull(message = "idCategory field cannot be null")
    private Integer idCategory;
    private String barCode;
    @NotNull(message = "sellingPrice field cannot be null")
    private Double sellingPrice;
    @NotNull(message = "stockQuantity field cannot be null")
    private Integer stockQuantity;
    @NotNull(message = "state field cannot be null")
    private Boolean state;
    @Column(name = "id_supplier")
    @NotNull(message = "supplierId field cannot be null")
    private Integer supplierId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_supplier", insertable = false, updatable = false)
    private Supplier supplier;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_category", insertable = false, updatable = false)
    private Category category;
}
