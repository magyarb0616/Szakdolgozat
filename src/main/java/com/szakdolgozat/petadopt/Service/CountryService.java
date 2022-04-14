package com.szakdolgozat.petadopt.Service;


import com.szakdolgozat.petadopt.DTO.CountryDTO;
import com.szakdolgozat.petadopt.DTO.IdDTO;
import com.szakdolgozat.petadopt.Exception.DependenceException;
import com.szakdolgozat.petadopt.Exception.NullException;
import com.szakdolgozat.petadopt.Exception.ResourceNotFoundException;
import com.szakdolgozat.petadopt.Model.Country;
import com.szakdolgozat.petadopt.Repository.CityRepository;
import com.szakdolgozat.petadopt.Repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CityRepository cityRepository;

    public void saveCountryValidate(CountryDTO data){
        if (!data.getName().isEmpty()){
            countryRepository.save(new Country(data.getName()));
        }else{
            throw new NullException("Country","name");
        }
    }

    public void deleteCountryValidate(IdDTO data){
        Country existingCountry = countryRepository.findById(data.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Country","id",data.getId() ));
        if(cityRepository.existsCityByCountry_Id(data.getId())){
            throw new DependenceException();
        }
        countryRepository.deleteById(data.getId());
    }

    public void updateCountryValidate(CountryDTO data){
        Country existingCountry = countryRepository.findById(data.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Country","id",data.getId() ));
        existingCountry.setName(data.getName());
        countryRepository.save(existingCountry);
    }
}
