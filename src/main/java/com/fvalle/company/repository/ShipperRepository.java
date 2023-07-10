package com.fvalle.company.repository;

import com.fvalle.company.dto.ShipperDto;
import com.fvalle.company.entity.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipperRepository extends JpaRepository<Shipper,Integer> {

    @Query(value =
            "UPDATE shippers " +
                    "SET name = :#{#shipperDto.shipperName} , phone = :#{#shipperDto.shipperPhone} " +
                    "WHERE id = :id", nativeQuery = true)
    @Modifying
    void updateShipper(@Param("shipperDto") ShipperDto shipperDto, Integer id);
}
