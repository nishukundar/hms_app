package com.hms.controller;

import com.hms.dto.CountryDto;
import com.hms.entity.AppUser;
import com.hms.entity.Country;
import com.hms.repository.PropertyRepository;
import com.hms.service.CountryService;
import com.hms.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/country")
public class CountryController {

    private CountryService countryService;

    private PropertyService propertyService;
    public CountryController(CountryService countryService, PropertyRepository propertyRepository, PropertyService propertyService) {
        this.countryService = countryService;
        this.propertyService = propertyService;
    }

    @PostMapping("/unq")
    public AppUser userDetails(@AuthenticationPrincipal AppUser user){
        return user;
    }

    //http://localhost:8081/api/v1/country/addCountry
    @PostMapping("/addCountry")
    public ResponseEntity<CountryDto> addCountry(@RequestBody Country country){
        CountryDto countryDto = countryService.addCountry(country);
        return new ResponseEntity<>(countryDto, HttpStatus.CREATED);
    }

    @PostMapping("/deleteCountry")
    public ResponseEntity<String> deleteCountry(Long id){
        propertyService.deletePropertyByCountry(id);
        countryService.deleteCountry(id);
        return  new ResponseEntity<>("Country deleted", HttpStatus.OK);
    }


}
