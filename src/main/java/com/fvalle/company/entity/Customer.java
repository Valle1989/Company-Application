package com.fvalle.company.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@Builder
@Entity
@Table(name = "customers")
public class Customer extends AuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Name field cannot be null")
    private String name;

    @NotNull(message = "Address field cannot be null")
    private String address;

    @NotNull(message = "City field cannot be null")
    private String city;

    @NotNull(message = "PostalCode field cannot be null")
    @Pattern(regexp = "^[0-9]{4}$", message = "The postal code must be 4 digits long")
    private String postalCode;

    @NotNull(message = "Country field cannot be null")
    private String country;

    @NotNull(message = "Phone field cannot be null")
    @Pattern(regexp = "^(?:(?:\\+|00)54[-\\s]?)?(?:\\()?0?(?:11|[2368]\\d)(?:\\))?(?:[-\\s]?\\d{4}){2}$",
            message = "The entered phone number is invalid.")
    private String phone;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    @OrderBy("idOrder DESC")
    private List<Order> orders;
}
