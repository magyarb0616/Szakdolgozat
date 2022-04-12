package com.szakdolgozat.petadopt.Controller;

import com.szakdolgozat.petadopt.Model.Country;
import com.szakdolgozat.petadopt.Service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="api/country")
public class CountryController {
    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService){
        this.countryService = countryService;
    }
    @GetMapping
    public List<Country> getCountry(){
        return countryService.getCountrys();
    }


}
