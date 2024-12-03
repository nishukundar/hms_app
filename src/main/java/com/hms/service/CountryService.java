package com.hms.service;

import com.hms.dto.CountryDto;
import com.hms.entity.Country;
import com.hms.repository.CountryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CountryService {

    private CountryRepository countryRepository;

    private ModelMapper modelMapper;


    public CountryService(CountryRepository countryRepository, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
    }


    public CountryDto addCountry(Country country){
        Country save = countryRepository.save(country);
        return modelMapper.map(country, CountryDto.class);
    }



    public void deleteCountry(Long id) {
        countryRepository.deleteById(id);
    }
}
