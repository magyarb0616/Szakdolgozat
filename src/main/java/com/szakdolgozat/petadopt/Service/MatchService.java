package com.szakdolgozat.petadopt.Service;

import com.szakdolgozat.petadopt.DTO.IdDTO;
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
    @Autowired
    private UserUtils userUtils;

    @Transactional
    public void createMatchValidate(IdDTO pet) {
        if (petRepository.existsById(pet.getId())) {
            matchRepository.save(new Match(userUtils.getRequestingUser(), petRepository.getById(pet.getId())));
            Pet existingPet = petRepository.getById(pet.getId());
            existingPet.incrementScore();
            petRepository.save(existingPet);
        }
        throw new ResourceNotFoundException("Pet", "id", pet.getId());
    }
}
