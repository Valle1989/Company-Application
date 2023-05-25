package com.fvalle.company.mapper;

import com.fvalle.company.dto.CategoryDto;
import com.fvalle.company.dto.OrderDto;
import com.fvalle.company.entity.Category;
import com.fvalle.company.entity.Order;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mappings({
            @Mapping(source = "idOrder", target = "id"),
            @Mapping(source = "userId", target = "id_user"),
            @Mapping(source = "customerId", target = "id_customer"),
            @Mapping(source = "employeeId", target = "id_employee"),
            @Mapping(source = "shipperId", target = "id_shipper")
    })
    OrderDto toOrderDto(Order order);
    List<OrderDto> toOrders(List<Order> orderList);

    @InheritInverseConfiguration
    Order toOrder(OrderDto orderDto);
}
