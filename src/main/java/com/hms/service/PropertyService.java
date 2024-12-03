package com.hms.service;

import com.hms.dto.PropertyDto;
import com.hms.entity.Property;
import com.hms.repository.PropertyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PropertyService {

    private PropertyRepository propertyRepository;

    private ModelMapper modelMapper;

    public PropertyService(PropertyRepository propertyRepository, ModelMapper modelMapper) {
        this.propertyRepository = propertyRepository;
        this.modelMapper = modelMapper;
    }

    public PropertyDto addProperty(Property property){
        Property property1 = propertyRepository.save(property);
        return modelMapper.map(property, PropertyDto.class);
    }


    public void deleteProperty(Long id){
        propertyRepository.deleteByCityId(id);
    }

    public void deletePropertyByCountry(Long id){
        propertyRepository.deleteByCountryId(id);
    }
}
