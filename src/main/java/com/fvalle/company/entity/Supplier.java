package com.fvalle.company.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "suppliers")
public class Supplier extends AuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "Name field cannot be null")
    private String name;
    @NotNull(message = "Address field cannot be null")
    private String address;
    @NotNull(message = "City field cannot be null")
    private String city;
    //@NotNull(message = "PostalCode field cannot be null")
    private String postalCode;
    //@NotNull(message = "Country field cannot be null")
    private String country;
    @NotNull(message = "Phone field cannot be null")
    private String phone;

    @OneToMany(mappedBy = "supplier", fetch = FetchType.EAGER)
    private List<Product> products;


}
