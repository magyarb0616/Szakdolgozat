package com.szakdolgozat.petadopt.Controller;

import com.szakdolgozat.petadopt.DTO.IdDTO;
import com.szakdolgozat.petadopt.DTO.Security.Response.MessageResponse;
import com.szakdolgozat.petadopt.Exception.NoRightException;
import com.szakdolgozat.petadopt.Exception.ResourceNotFoundException;
import com.szakdolgozat.petadopt.Model.Match;
import com.szakdolgozat.petadopt.Model.Pet;
import com.szakdolgozat.petadopt.Repository.MatchRepository;
import com.szakdolgozat.petadopt.Repository.PetRepository;
import com.szakdolgozat.petadopt.Service.MatchService;
import com.szakdolgozat.petadopt.Service.PetService;
import com.szakdolgozat.petadopt.Service.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/match")
public class MatchController {
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private MatchService matchService;
    @Autowired
    private UserUtils userUtils;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private PetService petService;

    @GetMapping
    public List<Map<String, String>> getAdopterMatches(){
        if (userUtils.isAdmin() == 0 || userUtils.isAdmin() == userUtils.getRequestingUser().getId()){
            List<Match> matches = matchRepository.findAllByAdopterID_Id(userUtils.getRequestingUser().getId());

            List<Map<String, String>> out = new ArrayList<>();
            if (matches.isEmpty()){ return out; } else {
                for (Match match : matches){
                    String id = match.getId().toString();
                    String petId = match.getPetID().getId().toString();
                    String adopterId = match.getAdopterID().getId().toString();

                    Pet pet = petRepository.getById(match.getPetID().getId());
                    String adoptiveId = pet.getAdoptive().getId().toString();
                    String name = pet.getName();
                    String age = pet.getAge().toString();
                    String sex = petService.petSex(pet.getSex());
                    String size = petService.petSize(pet.getSize());
                    String hair = petService.petHair(pet.getHair());
                    String movement = petService.petMovement(pet.getMovement());
                    String description = pet.getDescription();
                    String speciesId = pet.getBreed().getSpecies().getId().toString();
                    String speciesName = pet.getBreed().getSpecies().getName();
                    String breedId = pet.getBreed().getId().toString();
                    String breedName = pet.getBreed().getName();
                    String cityId = pet.getCity().getId().toString();
                    String cityName = pet.getCity().getName();
                    String countryName = pet.getCity().getCountry().getName();

                    Map<String, String> map = new LinkedHashMap<>();
                    map.put("id",id);
                    map.put("petId",petId);
                    map.put("adopterId",adopterId);
                    map.put("adoptiveId",adoptiveId);
                    map.put("name",name);
                    map.put("age",age);
                    map.put("sex",sex);
                    map.put("size",size);
                    map.put("hair",hair);
                    map.put("movement",movement);
                    map.put("description",description);
                    map.put("speciesId", speciesId);
                    map.put("speciesName", speciesName);
                    map.put("breedId",breedId);
                    map.put("breedName", breedName);
                    map.put("cityId",cityId);
                    map.put("cityName", cityName);
                    map.put("countryName", countryName);

                    out.add(map);
                }
            }
            return out;
        } else { throw new NoRightException("List","matches"); }

    }

    @PostMapping
    public ResponseEntity<?> createMatch(IdDTO data){
        System.out.println("kakaka");
        System.out.println(userUtils.getRequestingUser().getUsername());
        System.out.println(petRepository.existsById(data.getId())+"    "+petRepository.existsPetById(data.getId()));
        matchService.createMatchValidate(data);
        return ResponseEntity.ok("succesfull");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMatch(IdDTO data){
        if (matchRepository.existsById(data.getId())){
            if (userUtils.isAdmin() == 0 || userUtils.isAdmin() == matchRepository.getById(data.getId()).getAdopterID().getId()){
                matchRepository.deleteById(data.getId());
            } else { throw new NoRightException("delete","match"); }
        } else { throw new ResourceNotFoundException("Match","id",data.getId()); }
        return ResponseEntity.ok("Delete successfull!");
    }

}