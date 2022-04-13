package com.szakdolgozat.petadopt.Controller;

import com.szakdolgozat.petadopt.DTO.CountryDTO;
import com.szakdolgozat.petadopt.Model.Country;
import com.szakdolgozat.petadopt.Repository.CountryRepository;
import com.szakdolgozat.petadopt.Service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="api/country")
public class CountryController {
    @Autowired
    private CountryService countryService;
    @Autowired
    private CountryRepository countryRepository;


    @GetMapping (path= "/list")
    public List<Map<String, String>> getAll(){

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

    @PostMapping
    public void saveCountry(CountryDTO data){
        System.out.println(data.getName());
        countryRepository.save(new Country(data.getName()));
    }

    //@PutMapping
    //public void





}
