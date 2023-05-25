package com.fvalle.company.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {

    private Integer id;
    private Integer id_user;
    private Integer id_customer;
    private Integer id_employee;
    private Integer id_shipper;
}
