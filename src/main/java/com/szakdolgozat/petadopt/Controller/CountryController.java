package com.szakdolgozat.petadopt.Controller;

import com.szakdolgozat.petadopt.Model.Country;
import com.szakdolgozat.petadopt.Repository.CountryRepository;
import com.szakdolgozat.petadopt.Service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @GetMapping (path= "/countrys")
    public List<Map<String, String>> getAll(){
        List<Country> countrys = countryRepository.findAll();
        List<Map<String, String>> out = new ArrayList<Map<String, String>>();
        for(Country country : countrys){
            String id = country.getId().toString();
            String Name = country.getName();
            Map<String, String> map = new HashMap<>();
            map.put("name", Name);
            map.put("id", id);
            out.add(map);

        }
    return out;
    }


}
