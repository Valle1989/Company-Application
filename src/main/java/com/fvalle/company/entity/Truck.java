package com.fvalle.company.entity;

import com.fvalle.company.dto.TruckDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class Truck {

    private List<TruckDto> trucks;
    private TruckDto truck;

    public Truck(List<TruckDto> trucks) {
        this.trucks = trucks;
    }

    public Truck(TruckDto truck) {
        this.truck = truck;
    }
}
