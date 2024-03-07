package com.fvalle.company.controller;

import com.fvalle.company.entity.Shipper;
import com.fvalle.company.entity.ShipperWithTruck;
import com.fvalle.company.service.impl.ShipperWithTruckImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shipper/truck")
@AllArgsConstructor
public class ShipperWithTruckController {

    private final ShipperWithTruckImpl shipperWithTruckService;

    @GetMapping("/id/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<ShipperWithTruck> getAll(@PathVariable("id") Integer id){
        return new ResponseEntity<>(shipperWithTruckService.getById(id), HttpStatus.OK);
    }
}
