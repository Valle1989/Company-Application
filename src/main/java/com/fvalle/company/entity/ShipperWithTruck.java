package com.fvalle.company.entity;

import com.fvalle.company.dto.TruckDto;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ShipperWithTruck {

    private Shipper shipper;
    private TruckDto truckDto;
}
