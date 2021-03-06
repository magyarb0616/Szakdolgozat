package com.szakdolgozat.petadopt.Service;

import com.szakdolgozat.petadopt.DTO.IdDTO;
import com.szakdolgozat.petadopt.DTO.PetCreateDTO;
import com.szakdolgozat.petadopt.DTO.PetDTO;
import com.szakdolgozat.petadopt.Exception.InvalidParameterException;
import com.szakdolgozat.petadopt.Exception.NoRightException;
import com.szakdolgozat.petadopt.Exception.NullException;
import com.szakdolgozat.petadopt.Exception.ResourceNotFoundException;
import com.szakdolgozat.petadopt.Model.Pet;
import com.szakdolgozat.petadopt.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private UserUtils userUtils;

    public void createPetValidate(PetCreateDTO data){

        if (breedRepository.existsBreedById(data.getBreedId())){
            if (cityRepository.existsById(data.getCityId())){
                if (!data.getName().isEmpty()){
                    if (data.getSize()<=3 && data.getSize()>=1){
                        if (data.getHair()<=3 && data.getHair()>=1){
                            if (data.getMovement()<=3 && data.getMovement()>=1){
                                petRepository.save(new Pet(
                                        userUtils.getRequestingUser(),
                                        //userRepository.getById(data.getAdoptiveId()),
                                        data.getName(),
                                        data.getAge(),
                                        data.getSex(),
                                        data.getSize(),
                                        data.getHair(),
                                        data.getMovement(),
                                        "",
                                        0,
                                        breedRepository.getById(data.getBreedId()),
                                        cityRepository.getById(data.getCityId())));
                            } else { throw new InvalidParameterException("Pet","movement",data.getMovement()); }
                        } else { throw new InvalidParameterException("Pet","hair",data.getHair()); }
                    } else { throw new InvalidParameterException("Pet","size",data.getSize()); }
                }else { throw new NullException("Pet","name");}
            } else { throw new ResourceNotFoundException("City","id",data.getCityId()); }
        } else { throw new ResourceNotFoundException("Breed","id",data.getBreedId()); }
    }

    public void updatePetValidate(PetDTO data) {
        if (petRepository.existsById(data.getId())){
            //Only allows to delete users own animals if they don't have the admin role
            if ( userUtils.isAdmin()==0 || userUtils.isAdmin() == petRepository.getById(data.getId()).getAdoptive().getId() ){
                if (breedRepository.existsBreedById(data.getBreedId())){
                    if (cityRepository.existsById(data.getCityId())){
                        if (!data.getName().isEmpty()){
                            if (data.getSize()<=3 && data.getSize()>=1){
                                if (data.getHair()<=3 && data.getHair()>=1){
                                    if (data.getMovement()<=3 && data.getMovement()>=1){
                                        Pet existingPet = petRepository.getById(data.getId());
                                        //existingPet.setAdoptive(userRepository.getById(data.getAdoptiveId()));
                                        existingPet.setName(data.getName());
                                        existingPet.setAge(data.getAge());
                                        existingPet.setSex(data.getSex());
                                        existingPet.setSize(data.getSize());
                                        existingPet.setHair(data.getHair());
                                        existingPet.setMovement(data.getMovement());
                                        existingPet.setDescription(data.getDescription());
                                        existingPet.setBreed(breedRepository.getById(data.getBreedId()));
                                        existingPet.setCity(cityRepository.getById(data.getCityId()));
                                        petRepository.save(existingPet);

                                    } else { throw new InvalidParameterException("Pet","movement",data.getMovement()); }
                                } else { throw new InvalidParameterException("Pet","hair",data.getHair()); }
                            } else { throw new InvalidParameterException("Pet","size",data.getSize()); }
                        }else { throw new NullException("Pet","name");}
                    } else { throw new ResourceNotFoundException("City","id",data.getCityId()); }
                } else { throw new ResourceNotFoundException("Breed","id",data.getBreedId()); }
            }else { throw new NoRightException("update","pet"); }
        } else { throw new ResourceNotFoundException("Pet","id",data.getId()); }
    }

    @Transactional
    public void deletePetValidate(IdDTO data){
        if (petRepository.existsById(data.getId()))
        {
            if (userUtils.isAdmin()==0 || userUtils.isAdmin() == petRepository.getById(data.getId()).getAdoptive().getId()){
                if (matchRepository.existsByPetID_Id(data.getId())){
                    matchRepository.deleteAllByPetID_Id(data.getId());
                }
                if(imageRepository.existsImageByPetID_Id(data.getId())){
                    imageRepository.deleteAllByPetID_Id(data.getId());
                }
                petRepository.deleteById(data.getId());

            } else { throw new NoRightException("delete","pet"); }

        } else throw new ResourceNotFoundException("Pet","user",data.getId());
    }

    public String petHair(Integer value){
        switch (value){
            case 1 : return "Short";
            case 2 : return "Moderate";
            case 3 : return "Long";
            default: return "Unknown";
        }
    }

    public String petSize(Integer value){
        switch (value){
            case 1 : return "Small";
            case 2 : return "Medium";
            case 3 : return "Large";
            default: return "Unknown";
        }
    }

    public String petMovement(Integer value){
        switch (value){
            case 1 : return "Small";
            case 2 : return "Medium";
            case 3 : return "High";
            default: return "Unknown";
        }
    }

    public String petSex(Boolean value){
       if (true){
           return "Male";
    } else { return "Female"; }

}











}



