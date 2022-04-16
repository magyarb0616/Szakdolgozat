package com.szakdolgozat.petadopt.Service;

import com.szakdolgozat.petadopt.DTO.MatchDTO;
import com.szakdolgozat.petadopt.Exception.NullException;
import com.szakdolgozat.petadopt.Exception.ResourceNotFoundException;
import com.szakdolgozat.petadopt.Model.Match;
import com.szakdolgozat.petadopt.Model.Pet;
import com.szakdolgozat.petadopt.Repository.MatchRepository;
import com.szakdolgozat.petadopt.Repository.PetRepository;
import com.szakdolgozat.petadopt.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Component
@Service
public class MatchService {
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PetRepository petRepository;

    @Transactional
    public void createMatchValidate(MatchDTO data){
        if (data.getAdopterID() == 0 || data.getPetID() == 0){
            throw new NullException("userID or petID cannot be null or zero");
        } else if (userRepository.existsById(data.getAdopterID())){
            if (petRepository.existsById(data.getPetID())){
                matchRepository.save(new Match(userRepository.getById(data.getAdopterID()),
                        petRepository.getById(data.getPetID())));

                Pet pet = petRepository.getById(data.getPetID());
                pet.incrementScore();
                petRepository.save(pet);

            } else throw new ResourceNotFoundException("Pet","id",data.getPetID());
        } else throw new ResourceNotFoundException("User","id",data.getAdopterID());
    }


}
