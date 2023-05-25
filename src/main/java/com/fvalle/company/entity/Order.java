package com.fvalle.company.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fvalle.company.security.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOrder;

    @Column(name = "id_user")
    @NotNull(message = "userId field cannot be null")
    private Integer userId;

    @Column(name = "id_customer")
    @NotNull(message = "customerId field cannot be null")
    private Integer customerId;

    @Column(name = "id_employee")
    @NotNull(message = "employeeId field cannot be null")
    private Integer employeeId;

    @CreatedDate
    @NotNull
    @Column(nullable = false, updatable = false)
    private LocalDate orderDate;

    @Column(name = "id_shipper")
    @NotNull(message = "shipperId field cannot be null")
    private Integer shipperId;

    @ManyToOne
    @JoinColumn(name = "id_user", insertable = false, updatable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_customer", insertable = false, updatable = false)
    @JsonIgnore
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "id_employee", insertable = false, updatable = false)
    @JsonIgnore
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "id_shipper", insertable = false, updatable = false)
    @JsonIgnore
    private Shipper shipper;
}
