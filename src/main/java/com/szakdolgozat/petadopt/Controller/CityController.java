package com.szakdolgozat.petadopt.Controller;


import com.szakdolgozat.petadopt.DTO.CityDTO;
import com.szakdolgozat.petadopt.DTO.IdDTO;
import com.szakdolgozat.petadopt.Exception.ResourceNotFoundException;
import com.szakdolgozat.petadopt.Model.City;
import com.szakdolgozat.petadopt.Repository.CityRepository;
import com.szakdolgozat.petadopt.Service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "api/city")
public class CityController {
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CityService cityService;


    //Lists All cities
    @GetMapping(path = "/list")
    public List<Map<String, String>> citiesList(){
        List<City> cities = cityRepository.findAll();
        List<Map<String, String>> out = new ArrayList<>();

        for(City city : cities){
            String id = city.getId().toString();
            String name = city.getName();
            String countryId = city.getCountry().getId().toString();
            Map<String, String> map = new LinkedHashMap<>();
            map.put("id", id);
            map.put("name", name);
            map.put("countryId", countryId);
            out.add(map);
        }
        return out;
    }

    //Get a city by an ID
    @GetMapping
    public Map<String, String> getCitybyID(IdDTO data){
        City existingCity = cityRepository.findById(data.getId()).orElseThrow(
                () -> new ResourceNotFoundException("City","id",data.getId())
        );

        Map<String, String> map = new LinkedHashMap<>();
        map.put("id",existingCity.getId().toString());
        map.put("name", existingCity.getName());
        map.put("countryId", existingCity.getCountry().getId().toString());

        return map;
    }

    //Create a new city
    @PostMapping
    public ResponseEntity<?> createCity(CityDTO data){
        cityService.cityCreateValidate(data);
        return new ResponseEntity<>(null,HttpStatus.OK);
    }

    //Update a city
    @PutMapping
    public ResponseEntity<?> updateCity(CityDTO data){
        cityService.cityUpdateValidate(data);
        return new ResponseEntity<>(null,HttpStatus.OK);
    }

    //Delete a city by an ID
    @DeleteMapping
    public ResponseEntity<?> DeleteCity(IdDTO data){
        cityService.cityDeleteValidate(data);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}

