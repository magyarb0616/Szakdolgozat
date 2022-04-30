package com.szakdolgozat.petadopt.Controller;

import com.szakdolgozat.petadopt.DTO.SearchDTO;
import com.szakdolgozat.petadopt.Model.Pet;
import com.szakdolgozat.petadopt.Repository.ImageRepository;
import com.szakdolgozat.petadopt.Service.PetService;
import com.szakdolgozat.petadopt.Service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/search")
public class SearchController {


    @Autowired
    private SearchService searchService;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private PetService petService;


    @PostMapping
    public List<Map<String, String>> searchPet(@RequestBody SearchDTO data){
        List<Pet> pets = searchService.searchPet(data);
        List<Map<String, String>> out = new ArrayList<>();

        for (Pet pet: pets){
            String id = pet.getId().toString();
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
            String countryId = pet.getCity().getCountry().getId().toString();
            String cityName = pet.getCity().getName();
            String countryName = pet.getCity().getCountry().getName();
            String picturePath = "";
            if (imageRepository.existsImageByPetID_Id(pet.getId())){
                picturePath = imageRepository.getFirstByPetID_Id(pet.getId()).getPath();
            } else { picturePath = "" ;}

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
            map.put("speciesId", speciesId);
            map.put("speciesName", speciesName);
            map.put("breedId",breedId);
            map.put("breedName", breedName);
            map.put("cityId",cityId);
            map.put("picturePath",picturePath);
            map.put("countryId", countryId);
            map.put("countryName", countryName);
            map.put("cityName", cityName);

            out.add(map);
        }
        return out;
    }


}
