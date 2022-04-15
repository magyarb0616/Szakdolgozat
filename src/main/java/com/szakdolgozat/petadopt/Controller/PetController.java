package com.szakdolgozat.petadopt.Controller;

import com.szakdolgozat.petadopt.DTO.IdDTO;
import com.szakdolgozat.petadopt.DTO.PetDTO;
import com.szakdolgozat.petadopt.Exception.ResourceNotFoundException;
import com.szakdolgozat.petadopt.Model.Pet;
import com.szakdolgozat.petadopt.Repository.PetRepository;
import com.szakdolgozat.petadopt.Service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/pet")
public class PetController {
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private PetService petService;

    @GetMapping(path = "/list")
    public List<Map<String, String>> getAllPets(){
        List<Pet> pets = petRepository.findAll();
        List<Map<String, String>> out = new ArrayList<>();

        for (Pet pet: pets){
            String id = pet.getId().toString();
            String adoptiveId = pet.getAdoptive().getId().toString();
            String name = pet.getName();
            String age = pet.getAge().toString();
            String sex = pet.getSex().toString();
            String size = pet.getSize().toString();
            String hair = pet.getHair().toString();
            String movement = pet.getMovement().toString();
            String description = pet.getDescription();
            String breedId = pet.getBreed().getId().toString();
            String cityId = pet.getCity().getId().toString();

            Map<String, String> map = new LinkedHashMap<>();

            map.put("id",id);
            map.put("adoptiveId",adoptiveId);
            map.put("name",name);
            map.put("age",age);
            map.put("sex",sex);
            map.put("size",size);
            map.put("hair",hair);
            map.put("movement",movement);
            map.put("description",description);
            map.put("breedId",breedId);
            map.put("cityId",cityId);

            out.add(map);
        }
        return out;
    }

    @GetMapping
    public Map<String, String> getPetByID(IdDTO data){
        Pet existingPet = petRepository.findById(data.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Pet","id",data.getId()) );

        Map<String, String> map = new LinkedHashMap<>();

        String id = existingPet.getId().toString();
        String adoptiveId = existingPet.getAdoptive().getId().toString();
        String name = existingPet.getName();
        String age = existingPet.getAge().toString();
        String sex = existingPet.getSex().toString();
        String size = existingPet.getSize().toString();
        String hair = existingPet.getHair().toString();
        String movement = existingPet.getMovement().toString();
        String description = existingPet.getDescription();
        String breedId = existingPet.getBreed().getId().toString();
        String cityId = existingPet.getCity().getId().toString();

        map.put("id",id);
        map.put("adoptiveId",adoptiveId);
        map.put("name",name);
        map.put("age",age);
        map.put("sex",sex);
        map.put("size",size);
        map.put("hair",hair);
        map.put("movement",movement);
        map.put("description",description);
        map.put("breedId",breedId);
        map.put("cityId",cityId);
        return map;

    }


    @PostMapping
    public ResponseEntity<?> createPet(PetDTO data){
        petService.createPetValidate(data);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


}
