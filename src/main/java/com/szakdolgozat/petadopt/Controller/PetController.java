package com.szakdolgozat.petadopt.Controller;

import com.szakdolgozat.petadopt.DTO.IdDTO;
import com.szakdolgozat.petadopt.DTO.ImageDTO;
import com.szakdolgozat.petadopt.DTO.PetCreateDTO;
import com.szakdolgozat.petadopt.DTO.PetDTO;
import com.szakdolgozat.petadopt.Exception.ResourceNotFoundException;
import com.szakdolgozat.petadopt.Model.Pet;
import com.szakdolgozat.petadopt.Repository.ImageRepository;
import com.szakdolgozat.petadopt.Repository.PetRepository;
import com.szakdolgozat.petadopt.Service.ImageService;
import com.szakdolgozat.petadopt.Service.PetService;
import com.szakdolgozat.petadopt.Service.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
    @Autowired
    private ImageRepository imageRepository;

    @GetMapping(path = "/list")
    public List<Map<String, String>> getAllPets(){
        List<Pet> pets = petRepository.findAll();
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
            String breedId = pet.getBreed().getId().toString();
            String cityId = pet.getCity().getId().toString();
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
            map.put("breedId",breedId);
            map.put("cityId",cityId);
            map.put("picturePath",picturePath);

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
            map.put("cityName", cityName);
            map.put("countryId", countryId);
            map.put("countryName", countryName);
            map.put("picturePath",picturePath);

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
        String sex = petService.petSex(existingPet.getSex());
        String size = petService.petSize(existingPet.getSize());
        String hair = petService.petHair(existingPet.getHair());
        String movement = petService.petMovement(existingPet.getMovement());
        String description = existingPet.getDescription();
        String speciesId = existingPet.getBreed().getSpecies().getId().toString();
        String breedId = existingPet.getBreed().getId().toString();
        String cityId = existingPet.getCity().getId().toString();
        String picturePath = "";
        if (imageRepository.existsImageByPetID_Id(existingPet.getId())){
            picturePath = imageRepository.getFirstByPetID_Id(existingPet.getId()).getPath();
        } else { picturePath = "" ;}


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
        map.put("picturePath",picturePath);
        return map;

    }

@GetMapping(path = "/random")
public List<Map<String, String>> getRandomPets(){
    System.out.println("Requester: "+userUtils.getRequestingUser().getUsername());
    System.out.println("Count: "+petRepository.count());
    List<Pet> pets = new ArrayList<>();
    List<Map<String, String>> out = new ArrayList<>();

    if (petRepository.count()>=3){

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
            map.put("cityName", cityName);
            map.put("countryName", countryName);
            map.put("picturePath",picturePath);

            out.add(map);
        }
        return out;
    }else { return out; }



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

    @PostMapping(path = "/delete")
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

   /* @PostMapping(path = "/image")
    public ResponseEntity<?> createImage(ImageDTO data){
        imageService.createImage(data);
        return new ResponseEntity<>(null, HttpStatus.OK);
    } */

    @PostMapping(path = "/image/upload")
    public ResponseEntity<?> uploadImage(@RequestParam String id, @RequestParam("file") MultipartFile file){
        String filename = file.getOriginalFilename();
        String genFileName = id+"-"+System.currentTimeMillis()+"-"+filename;
        String storageAddress = System.getProperty("user.dir")+"\\src\\imageStorage\\";
        String imageAddress = storageAddress+genFileName;
        String httpserverAddress = "http://127.0.0.1:8081/"+genFileName;
        System.out.println("Image address: "+imageAddress);
        try{
            file.transferTo(new File(imageAddress));
            imageService.createImage(new ImageDTO(httpserverAddress,Long.parseLong(id)));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok("Picture uploaded successfully!");
    }


    @PostMapping(path = "/image/delete")
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
