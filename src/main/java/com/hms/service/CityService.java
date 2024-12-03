package com.hms.service;

import com.hms.dto.CityDto;
import com.hms.entity.City;
import com.hms.repository.CityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CityService {
    private CityRepository cityRepository;

    private ModelMapper modelMapper;

    public CityService(CityRepository cityRepository, ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }

    public CityDto addCity(City city){
        City save = cityRepository.save(city);
        return modelMapper.map(city, CityDto.class);
    }


    public void deleteCity(Long id){
        cityRepository.deleteById(id);
    }


}
