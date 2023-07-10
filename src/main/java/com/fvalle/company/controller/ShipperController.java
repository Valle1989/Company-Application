package com.fvalle.company.controller;

import com.fvalle.company.dto.CategoryDto;
import com.fvalle.company.dto.ShipperDto;
import com.fvalle.company.entity.Shipper;
import com.fvalle.company.service.IShipperService;
import com.fvalle.company.service.impl.ShipperServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shipper")
@AllArgsConstructor
public class ShipperController {

    private final IShipperService shipperService;
    private final ShipperServiceImpl shipperServiceImpl;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<Shipper> save(@Valid @RequestBody Shipper shipper){
        return new ResponseEntity<>(shipperService.save(shipper), HttpStatus.CREATED);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<ShipperDto> addShipper(@Valid @RequestBody ShipperDto shipperDto){
        return new ResponseEntity<>(shipperService.addShipper(shipperDto), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<Shipper>> getAll(){
        return new ResponseEntity<>(shipperService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/findAll")
    @PreAuthorize("hasAnyAuthority('user:read', 'admin:read')")
    public ResponseEntity<List<ShipperDto>> findAll(){
        return new ResponseEntity<>(shipperService.findAll(),HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<String> updatePrice(@RequestBody ShipperDto dto, @PathVariable("id") Integer id) {
        if (shipperServiceImpl.exists(id)) {
            shipperServiceImpl.updateShipper(dto, id);
            return new ResponseEntity<>("shipperDto with id " + id + " has been successfully modified", HttpStatus.OK);
        }

        return ResponseEntity.badRequest().build();
    }
}


