package com.szakdolgozat.petadopt.Service;

import com.szakdolgozat.petadopt.DTO.PetDTO;
import com.szakdolgozat.petadopt.Exception.InvalidParameterException;
import com.szakdolgozat.petadopt.Exception.NullException;
import com.szakdolgozat.petadopt.Exception.ResourceNotFoundException;
import com.szakdolgozat.petadopt.Model.Pet;
import com.szakdolgozat.petadopt.Repository.BreedRepository;
import com.szakdolgozat.petadopt.Repository.CityRepository;
import com.szakdolgozat.petadopt.Repository.PetRepository;
import com.szakdolgozat.petadopt.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private BreedRepository breedRepository;

    public void createPetValidate(PetDTO data){
        if (userRepository.existsById(data.getAdoptiveId())){
            if (breedRepository.existsBreedById(data.getBreedId())){
                if (cityRepository.existsById(data.getCityId())){
                    if (!data.getName().isEmpty()){
                        if (data.getSize()<=3 && data.getSize()>=1){
                            if (data.getHair()<=3 && data.getHair()>=1){
                                if (data.getMovement()<=3 && data.getMovement()>=1){
                                    petRepository.save(new Pet(
                                            userRepository.getById(data.getAdoptiveId()),
                                            data.getName(),
                                            data.getAge(),
                                            data.getSex(),
                                            data.getSize(),
                                            data.getHair(),
                                            data.getMovement(),
                                            data.getDescription(),
                                            0,
                                            breedRepository.getById(data.getBreedId()),
                                            cityRepository.getById(data.getCityId())));
                                } else { throw new InvalidParameterException("Pet","movement",data.getMovement()); }
                            } else { throw new InvalidParameterException("Pet","hair",data.getHair()); }
                        } else { throw new InvalidParameterException("Pet","size",data.getSize()); }
                    }else { throw new NullException("Pet","name");}
                } else { throw new ResourceNotFoundException("City","id",data.getCityId()); }
            } else { throw new ResourceNotFoundException("Breed","id",data.getBreedId()); }
        }else { throw new ResourceNotFoundException("User(Adoptive)","id",data.getAdoptiveId());}
    }






}
