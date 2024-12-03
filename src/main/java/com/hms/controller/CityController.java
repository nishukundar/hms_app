package com.hms.controller;

import com.hms.dto.CityDto;
import com.hms.entity.City;
import com.hms.service.CityService;
import com.hms.service.PropertyService;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/city")
public class CityController {

    //http://localhost:8081/api/v1/city/addCity
    private CityService cityService;
    private PropertyService propertyService;

    public CityController(CityService cityService, PropertyService propertyService) {
        this.cityService = cityService;
        this.propertyService = propertyService;
    }


    @PostMapping("/addCity")
    public ResponseEntity<CityDto> addCity(@RequestBody City city){
        CityDto cityDto = cityService.addCity(city);
        return new ResponseEntity<>(cityDto, HttpStatus.CREATED);
    }

    @PostMapping("/deleteCity")
    public ResponseEntity<String> deleteCity(@RequestParam Long id){
        propertyService.deletePropertyByCountry(id);
        cityService.deleteCity(id);
        return new ResponseEntity<>("City deleted", HttpStatus.OK);
    }



}
