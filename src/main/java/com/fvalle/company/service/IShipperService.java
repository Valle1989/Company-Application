package com.fvalle.company.service;

import com.fvalle.company.dto.CategoryDto;
import com.fvalle.company.dto.ShipperDto;
import com.fvalle.company.entity.Employee;
import com.fvalle.company.entity.Shipper;

import java.util.List;
import java.util.Optional;

public interface IShipperService {

    List<Shipper> getAll();
    List<ShipperDto> findAll();
    Optional<Shipper> getShipper(int id);
    Shipper save(Shipper shipper);

    ShipperDto addShipper(ShipperDto shipperDto);
}
