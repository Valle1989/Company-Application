package com.fvalle.company.service.impl;

import com.fvalle.company.dto.ShipperDto;
import com.fvalle.company.entity.Shipper;
import com.fvalle.company.mapper.ShipperMapper;
import com.fvalle.company.repository.ShipperRepository;
import com.fvalle.company.service.IShipperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShipperServiceImpl implements IShipperService {

    private final ShipperRepository shipperRepository;
    private final ShipperMapper shipperMapper;

    @Override
    public List<Shipper> getAll() {
        return shipperRepository.findAll();
    }

    public Shipper getByPhone(String phone){
        return shipperRepository.findByPhone(phone);
    }

    @Override
    public List<ShipperDto> findAll() {
        List<Shipper> shipperList = shipperRepository.findAll();
        return shipperMapper.toShippersDto(shipperList);
    }

    public boolean exists(int idShipper) {
        return this.shipperRepository.existsById(idShipper);
    }

    @Transactional
    public void updateShipper(ShipperDto dto, Integer id) {
        shipperRepository.updateShipper(dto,id);
    }

    @Override
    public Optional<Shipper> getShipper(int id) {
        return Optional.empty();
    }

    @Override
    @Transactional
    public Shipper save(Shipper shipper) {
        return shipperRepository.save(shipper);
    }

    @Override
    @Transactional
    public ShipperDto addShipper(ShipperDto shipperDto) {
        Shipper shipper = shipperMapper.toShipper(shipperDto);
        return shipperMapper.toShipperDto(shipperRepository.save(shipper));
    }
}
