package com.hms.controller;

import com.hms.dto.PropertyDto;
import com.hms.entity.Property;
import com.hms.repository.PropertyRepository;
import com.hms.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/a1/property")
public class PropertyController {

    //http://localhost:8081/api/a1/property/addProperty
    private PropertyService propertyService;
    private PropertyRepository propertyRepository;

    public PropertyController(PropertyService propertyService, PropertyRepository propertyRepository) {
        this.propertyService = propertyService;
        this.propertyRepository = propertyRepository;
    }

    @PostMapping("/addProperty")
    public ResponseEntity<PropertyDto> addProperty(@RequestBody Property property){
        PropertyDto propertyDto = propertyService.addProperty(property);
        return new ResponseEntity<>(propertyDto, HttpStatus.CREATED);
    }

    @GetMapping("/search-hotels")
    public List<Property> searchHotel(@RequestParam String name){
        List<Property> properties = propertyRepository.searchHotels(name);
        return properties;
    }
    }

