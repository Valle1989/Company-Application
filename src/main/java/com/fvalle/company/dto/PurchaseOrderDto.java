package com.fvalle.company.dto;

import com.fvalle.company.entity.Shipper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderDto {

    private Integer id;
    private CustomerDto customerDto;
    private EmployeeDto employeeDto;
    private ShipperDto shipperDto;
}
