package com.fvalle.company.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@Entity
@Table(name = "shippers")
public class Shipper extends AuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "name field cannot be null")
    private String name;
    @NotNull(message = "phone field cannot be null")
    private String phone;

    @OneToMany(mappedBy = "shipper", fetch = FetchType.EAGER)
    List<Order> orders;
}
