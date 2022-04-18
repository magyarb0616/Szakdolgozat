package com.szakdolgozat.petadopt.Service;


import com.szakdolgozat.petadopt.DTO.IdDTO;
import com.szakdolgozat.petadopt.DTO.ImageDTO;
import com.szakdolgozat.petadopt.Exception.AlreadyExistsException;
import com.szakdolgozat.petadopt.Exception.NoRightException;
import com.szakdolgozat.petadopt.Exception.ResourceNotFoundException;
import com.szakdolgozat.petadopt.Model.Image;
import com.szakdolgozat.petadopt.Repository.ImageRepository;
import com.szakdolgozat.petadopt.Repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private UserUtils userUtils;


    public List<Map<String, String>> getAllImagesByPet(IdDTO data){
        if (petRepository.existsById(data.getId())){

            List<Image> images = imageRepository.findAllByPetIDId(data.getId());
            List<Map<String, String>> out = new ArrayList<>();

            for (Image image : images){
                String id = image.getId().toString();
                String path = image.getPath();
                String petId = petRepository.getById(data.getId()).getId().toString();
                Map<String, String> map = new LinkedHashMap<>();
                map.put("id",id);
                map.put("path",path);
                map.put("petId",petId);
                out.add(map);
            }
            return out;
        }else { throw new ResourceNotFoundException("Pet","id",data.getId()); }
    }

    public void deleteImage(IdDTO data){
        if (imageRepository.existsById(data.getId())){
            if (userUtils.isAdmin() == 0 || userUtils.isAdmin() == imageRepository.getById(data.getId()).getPetID().getAdoptive().getId()){
                imageRepository.deleteById(data.getId());
            } else throw new NoRightException("delete","image");
        } else throw new ResourceNotFoundException("Image","id",data.getId());
    }

    public void createImage(ImageDTO data){
        if (petRepository.existsById(data.getPetID())){
            if (userUtils.isAdmin() == 0 || userUtils.isAdmin() == petRepository.getById(data.getPetID()).getAdoptive().getId()){
                if (!imageRepository.existsImageByPath(data.getPath())){
                    imageRepository.save(new Image(data.getPath(), petRepository.getById(data.getPetID())));
                }else { throw new AlreadyExistsException("Pet","path",data.getPath()); }
            } else { throw new NoRightException("create","image"); }
        } else { throw new ResourceNotFoundException("Pet","id",data.getPetID()); }


    }


}
