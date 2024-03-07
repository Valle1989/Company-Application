package com.fvalle.company.service.impl;

import com.fvalle.company.dto.ShipperDto;
import com.fvalle.company.dto.TruckDto;
import com.fvalle.company.entity.Shipper;
import com.fvalle.company.mapper.ShipperMapper;
import com.fvalle.company.repository.ShipperRepository;
import com.fvalle.company.service.IShipperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShipperServiceImpl implements IShipperService {

    private final ShipperRepository shipperRepository;
    private final ShipperMapper shipperMapper;
    private final TruckServiceImpl truckService;

    @Override
    public List<Shipper> getAll() {
        return shipperRepository.findAll();
    }

    public Shipper getByPhone(String phone){
        return shipperRepository.findByPhone(phone);
    }

    @Override
    public List<ShipperDto> findAll() {
        List<ShipperDto> list = new ArrayList<>();
        List<TruckDto> trucks = truckService.getAll();
        List<Shipper> shipperList = shipperRepository.findAll();

        shipperList.stream()
                .forEach(sl -> {
                    trucks.stream()
                            .forEach(t -> {
                                if(sl.getId() == t.getId()){
                                    ShipperDto shipperDto = shipperMapper.toShipperDto(sl);
                                    ShipperDto shi = new ShipperDto(shipperDto.getShipperName(),shipperDto.getShipperPhone(),t);
                                    list.add(shi);
                                }
                            });
                });

        return list;
    }

    public ShipperDto getWithTruckById(Integer id){
        Shipper shipper = getAll().stream()
                .filter(s -> s.getId() == id)
                .findFirst().get();
        ShipperDto shipperDto = shipperMapper.toShipperDto(shipper);
        TruckDto truckDto = truckService.getAll().stream().filter(t -> t.getId() == id).findFirst().get();
        ShipperDto shipperDtoFinal = new ShipperDto(shipperDto.getShipperName(), shipperDto.getShipperPhone(), truckDto);
        return shipperDtoFinal;
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
