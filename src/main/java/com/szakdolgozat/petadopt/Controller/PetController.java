package com.szakdolgozat.petadopt.Controller;

import com.szakdolgozat.petadopt.DTO.IdDTO;
import com.szakdolgozat.petadopt.DTO.ImageDTO;
import com.szakdolgozat.petadopt.DTO.PetCreateDTO;
import com.szakdolgozat.petadopt.DTO.PetDTO;
import com.szakdolgozat.petadopt.Exception.ResourceNotFoundException;
import com.szakdolgozat.petadopt.Model.Pet;
import com.szakdolgozat.petadopt.Repository.PetRepository;
import com.szakdolgozat.petadopt.Service.ImageService;
import com.szakdolgozat.petadopt.Service.PetService;
import com.szakdolgozat.petadopt.Service.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "api/pet")
public class PetController {
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private PetService petService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private UserUtils userUtils;

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
            String speciesId = pet.getBreed().getSpecies().getId().toString();
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
            map.put("speciesId", speciesId);
            map.put("breedId",breedId);
            map.put("cityId",cityId);

            out.add(map);
        }
        return out;
    }

    @GetMapping(path = "/mypets")
    public List<Map<String, String>> getMyPets(){
        List<Pet> pets = petRepository.getAllByAdoptive_Id(userUtils.getRequestingUser().getId());
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
            String speciesId = pet.getBreed().getSpecies().getId().toString();
            String speciesName = pet.getBreed().getSpecies().getName();
            String breedId = pet.getBreed().getId().toString();
            String breedName = pet.getBreed().getName();
            String cityId = pet.getCity().getId().toString();
            String countryId = pet.getCity().getCountry().getId().toString();
            String cityName = pet.getCity().getName();
            String countryName = pet.getCity().getCountry().getName();

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
            map.put("cityName", cityName);
            map.put("countryId", countryId);
            map.put("countryName", countryName);

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
        String speciesId = existingPet.getBreed().getSpecies().getId().toString();
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
        map.put("speciesId", speciesId);
        map.put("breedId",breedId);
        map.put("cityId",cityId);
        return map;

    }

@GetMapping(path = "/random")
public List<Map<String, String>> getRandomPets(){
    System.out.println("Requester: "+userUtils.getRequestingUser().getUsername());
    List<Pet> pets = new ArrayList<>();
    Long one = getRandomPetId();
    Long two = getRandomPetId();
    Long three = getRandomPetId();

        do{
            one = getRandomPetId();
            two = getRandomPetId();
            three = getRandomPetId();
        }while (one.equals(two) || one.equals(three) || two.equals(three));

    System.out.println("A: "+one+", B: "+two+", C: "+three);

    pets.add(petRepository.getById(one));
    pets.add(petRepository.getById(two));
    pets.add(petRepository.getById(three));

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
        String cityName = pet.getCity().getName();
        String countryName = pet.getCity().getCountry().getName();
        String countryId = pet.getCity().getCountry().getId().toString();

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
        map.put("cityName", cityName);
        map.put("countryName", countryName);

        out.add(map);
    }
    return out;

}

    @PostMapping
    public ResponseEntity<?> createPet(@RequestBody PetCreateDTO data){
        System.out.println(userUtils.getRequestingUser().getUsername());
        petService.createPetValidate(data);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updatePet(@RequestBody PetDTO data){
        petService.updatePetValidate(data);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deletePet(IdDTO data){
        petService.deletePetValidate(data);
        return ResponseEntity.ok("Delete successfull!");
    }
    //------ Image ------//
    @GetMapping(path = "/image/list")
    public List<Map<String, String>> getPetImages(IdDTO data) {
        List<Map<String, String>> out = imageService.getAllImagesByPet(data);
        return out;
    }

    @PostMapping(path = "/image")
    public ResponseEntity<?> createImage(ImageDTO data){
        imageService.createImage(data);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping(path = "/image")
    public ResponseEntity<?> deleteImage(IdDTO data){
        imageService.deleteImage(data);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    public Long getRandomPetId(){
        long leftLimit = 1L;
        long rightLimit = petRepository.count();
        System.out.println("db: "+rightLimit);
        long generatedLong;
        do {
            generatedLong = leftLimit + (long) (Math.random() * rightLimit);
        }while (!petRepository.existsPetById(generatedLong));
        return generatedLong;
    }

}
