package com.szakdolgozat.petadopt.Service;


import com.szakdolgozat.petadopt.Model.Country;
import com.szakdolgozat.petadopt.Repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
@Component
@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;


}
