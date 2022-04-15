package com.szakdolgozat.petadopt.Service;

import com.szakdolgozat.petadopt.DTO.IdDTO;
import com.szakdolgozat.petadopt.DTO.SpeciesDTO;
import com.szakdolgozat.petadopt.Exception.AlreadyExistsException;
import com.szakdolgozat.petadopt.Exception.DependenceException;
import com.szakdolgozat.petadopt.Exception.NullException;
import com.szakdolgozat.petadopt.Exception.ResourceNotFoundException;
import com.szakdolgozat.petadopt.Model.Species;
import com.szakdolgozat.petadopt.Repository.BreedRepository;
import com.szakdolgozat.petadopt.Repository.SpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class SpeciesService {
    @Autowired
    private SpeciesRepository speciesRepository;
    @Autowired
    private BreedRepository breedRepository;

    public void speciesCreateValidate(SpeciesDTO data){
        if (speciesRepository.existsSpeciesByName(data.getName())){
            throw new AlreadyExistsException("Species","name",data.getName());
        } else if (data.getName().isEmpty()) {
            throw new NullException("Species","Name");
        } else{ speciesRepository.save(new Species(data.getName())); }

    }

    public void speciesUpdateValidate(SpeciesDTO data){
        Species existingSpecies = speciesRepository.findById(data.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Species", "id", data.getId() ));
        if (!data.getName().isEmpty()){
            existingSpecies.setName(data.getName());
            speciesRepository.save(existingSpecies);
        }else { throw new NullException("Species","name"); }
    }

    public void speciesDeleteValidate(IdDTO data){
        Species existingSpecies = speciesRepository.findById(data.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Species", "id", data.getId() ));
        if (breedRepository.existsBreedBySpecies_Id(data.getId())){
            throw new DependenceException("At least one Breed depends on this Specie.");
        } else { speciesRepository.deleteById(data.getId()); }


    }

}
