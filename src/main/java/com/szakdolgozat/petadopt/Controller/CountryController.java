package com.szakdolgozat.petadopt.Controller;

import com.szakdolgozat.petadopt.DTO.CountryDTO;
import com.szakdolgozat.petadopt.DTO.IdDTO;
import com.szakdolgozat.petadopt.Exception.ResourceNotFoundException;
import com.szakdolgozat.petadopt.Model.City;
import com.szakdolgozat.petadopt.Model.Country;
import com.szakdolgozat.petadopt.Repository.CityRepository;
import com.szakdolgozat.petadopt.Repository.CountryRepository;
import com.szakdolgozat.petadopt.Service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path="api/country")
public class CountryController {
    @Autowired
    private CountryService countryService;
    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CityRepository cityRepository;

    @GetMapping
    public Map<String, String> getCountrybyID(IdDTO data){
        Optional<Country> country = countryRepository.findById(data.getId());
        if(country.isPresent()){
            Map<String, String> map = new HashMap<>();
            map.put("id",country.get().getId().toString());
            map.put("name", country.get().getName());
            return map;
        } else{
            throw new ResourceNotFoundException("Country","id",data.getId());
        }
    }

    @GetMapping (path= "/list")
    public List<Map<String, String>> getAllCountry() {

        List<Country> countrys = countryRepository.findAll();
        List<Map<String, String>> out = new ArrayList<Map<String, String>>();

        for(Country country : countrys){
            String id = country.getId().toString();
            String name = country.getName();
            Map<String, String> map = new HashMap<>();
            map.put("id", id);
            map.put("name", name);
            out.add(map);
        }
    return out;
    }

    @GetMapping(path = "/cities")
    public List<Map<String, String>> getCountryCities(IdDTO data){
        if (countryRepository.existsCountryById(data.getId())){
            List<City> cities = cityRepository.findAllByCountry_Id(data.getId());
            List<Map<String, String>> out = new ArrayList<Map<String, String>>();

            for(City city : cities){
                String id = city.getId().toString();
                String name = city.getName();
                String countryid = city.getCountry().getId().toString();
                Map<String, String> map = new HashMap<>();
                map.put("id", id);
                map.put("name", name);
                map.put("countryid",countryid);
                out.add(map);
            }
            return out;
        } else { throw new ResourceNotFoundException("Country","id",data.getId()); }
    }

    @PostMapping
    public ResponseEntity<?> saveCountry(CountryDTO data){
        countryService.saveCountryValidate(data);
        return new ResponseEntity<>(null,HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateCountry(CountryDTO data){
        countryService.updateCountryValidate(data);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCountry(IdDTO data){
        countryService.deleteCountryValidate(data);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


}
