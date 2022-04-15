package com.szakdolgozat.petadopt.Controller;


import com.szakdolgozat.petadopt.DTO.BreedDTO;
import com.szakdolgozat.petadopt.DTO.IdDTO;
import com.szakdolgozat.petadopt.Exception.ResourceNotFoundException;
import com.szakdolgozat.petadopt.Model.Breed;
import com.szakdolgozat.petadopt.Repository.BreedRepository;
import com.szakdolgozat.petadopt.Service.BreedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "api/breed")
public class BreedController {
    @Autowired
    private BreedRepository breedRepository;
    @Autowired
    private BreedService breedService;


    @GetMapping(path = "/list")
    public List<Map<String, String>> breedsList(){
        List<Breed> breeds = breedRepository.findAll();
        List<Map<String, String>> out = new ArrayList<>();

        for(Breed breed : breeds){
            String id = breed.getId().toString();
            String name = breed.getName();
            String speciesId=breed.getSpecies().getId().toString();
            Map<String, String> map = new LinkedHashMap<>();
            map.put("id",id);
            map.put("name",name);
            map.put("speciesId",speciesId);
            out.add(map);
        }
        return out;
    }

    @GetMapping
    public Map<String, String> getBreedByID(IdDTO data){
        Breed existingBreed = breedRepository.findById(data.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Breed", "id",data.getId()) );

        Map<String, String> map = new LinkedHashMap<>();
        String id = existingBreed.getId().toString();
        String name = existingBreed.getName();
        String speciesId=existingBreed.getSpecies().getId().toString();
        map.put("id",id);
        map.put("name",name);
        map.put("speciesId",speciesId);
        return map;
    }

    @PostMapping
    public ResponseEntity<?> createBreed(BreedDTO data){
        breedService.createBreedValidate(data);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateBreed(BreedDTO data){
        breedService.updateBreedValidate(data);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteBreed(IdDTO data){
        breedService.deleteBreedValidate(data);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


}
