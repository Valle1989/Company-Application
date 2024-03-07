package com.fvalle.company.controller;

import com.fvalle.company.dto.TruckDto;
import com.fvalle.company.entity.Customer;
import com.fvalle.company.entity.Truck;
import com.fvalle.company.service.impl.TruckServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trucks")
@AllArgsConstructor
public class TruckController {

    private final TruckServiceImpl truckService;

    @GetMapping()
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<Truck> getAll(){
        return new ResponseEntity<>(new Truck(truckService.getAll()), HttpStatus.OK);
    }

    @GetMapping("id/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<TruckDto> getById(@PathVariable("id") Integer id){
        return new ResponseEntity<>(truckService.getById(id), HttpStatus.OK);
    }
}
