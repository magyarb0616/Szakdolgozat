package com.szakdolgozat.petadopt.Service;


import com.szakdolgozat.petadopt.DTO.CityDTO;
import com.szakdolgozat.petadopt.DTO.IdDTO;
import com.szakdolgozat.petadopt.Exception.AlreadyExistsException;
import com.szakdolgozat.petadopt.Exception.DependenceException;
import com.szakdolgozat.petadopt.Exception.NullException;
import com.szakdolgozat.petadopt.Exception.ResourceNotFoundException;
import com.szakdolgozat.petadopt.Model.City;
import com.szakdolgozat.petadopt.Repository.CityRepository;
import com.szakdolgozat.petadopt.Repository.CountryRepository;
import com.szakdolgozat.petadopt.Repository.PetRepository;
import com.szakdolgozat.petadopt.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PetRepository petRepository;

    public void CitySaveValidate(CityDTO data){
        if (cityRepository.existsCityByName(data.getName())){
            throw new AlreadyExistsException("City","name",data.getName());
        } else if (data.getName().isEmpty()) {
            throw new NullException("City","Name");
        } else if (!countryRepository.existsCountryById(data.getCountryId())) {
            throw new ResourceNotFoundException("Country","id",data.getCountryId());
        } else {
            cityRepository.save(new City(data.getName(),
                                    countryRepository.getById(data.getCountryId())));
        }
    }

    public void CityDeleteValidate(IdDTO data){
        if (cityRepository.existsById(data.getId()))
        {
            if(userRepository.existsUserByCity_Id(data.getId()) || petRepository.existsPetByCity_Id(data.getId()))
            {
                throw new DependenceException();
            }else { cityRepository.deleteById(data.getId()); }
        }else {
            throw new ResourceNotFoundException("City","id",data.getId());
        }
    }

    public void CityUpdateValidate(CityDTO data){
        City existingCity = cityRepository.findById(data.getId()).orElseThrow(
                () -> new ResourceNotFoundException("City","id",data.getId()));
        if (data.getName().isEmpty())
        { throw new NullException("City","name"); }
        else if (countryRepository.existsCountryById(data.getCountryId()))
            {
                existingCity.setName(data.getName());
                existingCity.setCountry(countryRepository.getById(data.getCountryId()));
                cityRepository.save(existingCity);
            } else{ throw new ResourceNotFoundException("Country","id",data.getCountryId()); }


    }
}
