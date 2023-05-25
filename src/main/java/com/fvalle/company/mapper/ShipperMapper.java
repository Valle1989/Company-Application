package com.fvalle.company.mapper;

import com.fvalle.company.dto.CategoryDto;
import com.fvalle.company.dto.ShipperDto;
import com.fvalle.company.entity.Category;
import com.fvalle.company.entity.Shipper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShipperMapper {

    @Mappings({
            @Mapping(source = "name", target = "shipperName"),
            @Mapping(source = "phone", target = "shipperPhone"),
    })
    ShipperDto toShipperDto(Shipper shipper);
    List<ShipperDto> toShippersDto(List<Shipper> shipperList);

    @InheritInverseConfiguration
    Shipper toShipper(ShipperDto shipperDto);
}
