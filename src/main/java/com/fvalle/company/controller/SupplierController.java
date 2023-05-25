package com.fvalle.company.controller;

import com.fvalle.company.dto.CategoryDto;
import com.fvalle.company.dto.SupplierWithProductDto;
import com.fvalle.company.entity.Supplier;
import com.fvalle.company.service.ISupplierService;
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

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<SupplierWithProductDto>> getAll(){
        return new ResponseEntity<>(supplierService.getAll(),HttpStatus.OK);
    }
}
