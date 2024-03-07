package com.fvalle.company.service.impl;

import com.fvalle.company.entity.ShipperWithTruck;
import com.fvalle.company.repository.ShipperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipperWithTruckImpl {

    private final ShipperRepository shipperRepository;
    private final TruckServiceImpl truckService;

    public ShipperWithTruck getById(Integer id){
        ShipperWithTruck shipper = new ShipperWithTruck(
                shipperRepository.findAll().stream().filter(s -> s.getId() == id).findFirst().get(),
                truckService.getAll().stream().filter(t -> t.getId() == id).findFirst().get()
        );
        return  shipper;
    }
}
