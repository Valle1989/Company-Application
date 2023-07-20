package com.fvalle.company.controller;

import com.fvalle.company.dto.CategoryDto;
import com.fvalle.company.dto.SupplierDto;
import com.fvalle.company.dto.SupplierWithProductDto;
import com.fvalle.company.entity.Supplier;
import com.fvalle.company.service.ISupplierService;
import com.fvalle.company.service.impl.SupplierServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplier")
@AllArgsConstructor
public class SupplierController {

    private final ISupplierService supplierService;

    private final SupplierServiceImpl supplierServiceImpl;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<SupplierWithProductDto> addSupplierWithProduct(@Valid @RequestBody SupplierWithProductDto supplierWithProductDto){
        return new ResponseEntity<>(supplierService.save(supplierWithProductDto), HttpStatus.CREATED);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<Supplier> addSupplier(@Valid @RequestBody Supplier supplier){
        return new ResponseEntity<>(supplierService.add(supplier), HttpStatus.CREATED);
    }

    @GetMapping("/find/all")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<SupplierDto>> findAll(){
        return new ResponseEntity<>(supplierService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/city")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<Supplier>> getSupplierByCity(@RequestParam String city){
        return new ResponseEntity<>(supplierServiceImpl.getSupplierByCity(city),HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<Supplier>> findSuppliersByName(@PathVariable("name") String name){
        return new ResponseEntity<>(supplierServiceImpl.findSuppliersByName(name),HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<SupplierWithProductDto>> getAll(){
        return new ResponseEntity<>(supplierService.getAll(),HttpStatus.OK);
    }
}
