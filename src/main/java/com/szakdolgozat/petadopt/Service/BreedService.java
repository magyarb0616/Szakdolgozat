package com.szakdolgozat.petadopt.Service;

import com.szakdolgozat.petadopt.DTO.BreedDTO;
import com.szakdolgozat.petadopt.DTO.IdDTO;
import com.szakdolgozat.petadopt.Exception.AlreadyExistsException;
import com.szakdolgozat.petadopt.Exception.DependenceException;
import com.szakdolgozat.petadopt.Exception.NullException;
import com.szakdolgozat.petadopt.Exception.ResourceNotFoundException;
import com.szakdolgozat.petadopt.Model.Breed;
import com.szakdolgozat.petadopt.Repository.BreedRepository;
import com.szakdolgozat.petadopt.Repository.PetRepository;
import com.szakdolgozat.petadopt.Repository.SpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class BreedService {
    @Autowired
    private BreedRepository breedRepository;
    @Autowired
    private SpeciesRepository speciesRepository;
    @Autowired
    private PetRepository petRepository;

    public void createBreedValidate(BreedDTO data){
        if (breedRepository.existsBreedByName(data.getName())){
            throw new AlreadyExistsException("Breed","name",data.getName());
        } else if (data.getName().isEmpty()) {
            throw  new NullException("Breed","name");
        } else if (!speciesRepository.existsSpeciesById(data.getSpeciesId())) {
            throw new ResourceNotFoundException("Species","id",data.getSpeciesId());
        } else {
            breedRepository.save(new Breed(data.getName(),
                    speciesRepository.getById(data.getSpeciesId())));
        }
    }


    public void updateBreedValidate(BreedDTO data){
        Breed existingBreed = breedRepository.findById(data.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Breed","id",data.getId()) );
        if(data.getName().isEmpty()){
            throw new NullException("Breed","name");
        } else if (speciesRepository.existsSpeciesById(data.getSpeciesId())){
            existingBreed.setName(data.getName());
            existingBreed.setSpecies(speciesRepository.getById(data.getSpeciesId()));
            breedRepository.save(existingBreed);
        } else { throw new ResourceNotFoundException("Spedcies","id",data.getSpeciesId());  }
    }

    public void deleteBreedValidate(IdDTO data){
        if(breedRepository.existsBreedById(data.getId())){
            if (!petRepository.existsPetByBreedId(data.getId())){
                breedRepository.deleteById(data.getId());
            } else { throw new DependenceException("There is at least on Pet depends from this Breed"); }
        }else { throw new ResourceNotFoundException("Breed","id",data.getId()); }
    }


}
