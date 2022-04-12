package com.szakdolgozat.petadopt.Service;


import com.szakdolgozat.petadopt.Model.Country;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
@Component
@Service
public class CountryService {


    public List<Country> getCountrys() {
        return List.of(
                new Country(1,"Magyarország")
        );
    }

}
