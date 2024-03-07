package com.fvalle.company.service.impl;

import com.exisoft.javatemplate.config.RestTemplateConfig;
import com.fvalle.company.dto.TruckDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TruckServiceImpl {

    private final RestTemplateConfig restTemplateConfig;

    public List<TruckDto> getAll(){
        TruckDto[] trucks = restTemplateConfig.restTemplate().getForObject("https://64be74865ee688b6250c6ef3.mockapi.io/api/v1/trucks",TruckDto[].class);
        return Arrays.asList(trucks);
    }

    public TruckDto getById(Integer id){
        return restTemplateConfig.restTemplate().getForObject("https://64be74865ee688b6250c6ef3.mockapi.io/api/v1/trucks/"+id,TruckDto.class);
    }

}
