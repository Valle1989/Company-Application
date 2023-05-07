package com.fvalle.company.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orderDetails")*/
public class OrderDetail {

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "orderId field cannot be null")
    private Integer orderId;
    @NotNull(message = "productId field cannot be null")
    private Integer productId;
    @NotNull(message = "quantity field cannot be null")
    private Integer quantity;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_orderDetail", insertable = false, updatable = false)
    private Order order;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_product", insertable = false, updatable = false)
    private Product product;*/
}
