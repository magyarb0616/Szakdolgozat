package com.szakdolgozat.petadopt.Controller;


import com.szakdolgozat.petadopt.DTO.IdDTO;
import com.szakdolgozat.petadopt.DTO.SpeciesDTO;
import com.szakdolgozat.petadopt.Exception.ResourceNotFoundException;
import com.szakdolgozat.petadopt.Model.Breed;
import com.szakdolgozat.petadopt.Model.Species;
import com.szakdolgozat.petadopt.Repository.BreedRepository;
import com.szakdolgozat.petadopt.Repository.SpeciesRepository;
import com.szakdolgozat.petadopt.Service.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "api/species")
public class SpeciesController {
    @Autowired
    private SpeciesRepository speciesRepository;
    @Autowired
    private SpeciesService speciesService;
    @Autowired
    private BreedRepository breedRepository;

    @GetMapping(path = "/list")
    public List<Map<String, String>> getAllSpecies(){
        List<Species> specieslist = speciesRepository.findAll();
        List<Map<String, String>> out = new ArrayList<Map<String, String>>();

        for (Species species : specieslist ){
            String id = species.getId().toString();
            String name = species.getName();
            Map<String, String> map = new LinkedHashMap<>();
            map.put("id", id);
            map.put("name", name);
            out.add(map);
        }
        return out;
    }

    @GetMapping
    public Map<String, String> getSpeciesByID(IdDTO data){
        Species existingSpecies = speciesRepository.findById(data.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Species","name",data.getId())
        );
        Map<String, String> map = new LinkedHashMap<>();
        map.put("id",existingSpecies.getId().toString());
        map.put("name",existingSpecies.getName());
        return map;
    }

    @GetMapping(path = "/breeds")
    public List<Map<String, String>>  getSpecieBreeds(IdDTO data){
        if (speciesRepository.existsSpeciesById(data.getId())){
            List<Breed> breeds = breedRepository.findAllBySpecies_Id(data.getId());
            List<Map<String, String>> out = new ArrayList<Map<String, String>>();

            for (Breed breed: breeds){
                String id = breed.getId().toString();
                String name = breed.getName();
                Map<String, String> map = new HashMap<>();
                map.put("id", id);
                map.put("name", name);
                out.add(map);
            }
        return out;

        }else { throw new ResourceNotFoundException("Specie","id",data.getId()); }
    }


    @PostMapping
    public ResponseEntity<?> createSpecies(SpeciesDTO data){
        speciesService.speciesCreateValidate(data);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateSpecies(SpeciesDTO data){
        speciesService.speciesUpdateValidate(data);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteSpecies(IdDTO data){
        speciesService.speciesDeleteValidate(data);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }




















}
