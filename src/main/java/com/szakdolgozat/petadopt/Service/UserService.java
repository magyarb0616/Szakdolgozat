package com.szakdolgozat.petadopt.Service;

import com.szakdolgozat.petadopt.DTO.IdDTO;
import com.szakdolgozat.petadopt.DTO.Security.Request.PasswordChangeRequest;
import com.szakdolgozat.petadopt.DTO.UserUpdateDTO;
import com.szakdolgozat.petadopt.Exception.AlreadyExistsException;
import com.szakdolgozat.petadopt.Exception.InvalidParameterException;
import com.szakdolgozat.petadopt.Exception.NoRightException;
import com.szakdolgozat.petadopt.Exception.ResourceNotFoundException;
import com.szakdolgozat.petadopt.Model.Pet;
import com.szakdolgozat.petadopt.Model.User;
import com.szakdolgozat.petadopt.Repository.CityRepository;
import com.szakdolgozat.petadopt.Repository.PetRepository;
import com.szakdolgozat.petadopt.Repository.UserRepository;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private PetService petService;
    @Autowired
    private UserUtils userUtils;
    @Autowired
    private PasswordEncoder encoder;

    public void updateUserValidate(UserUpdateDTO data){
        if (userUtils.isAdmin() == 0 || userUtils.isAdmin() == data.getId()){
            if (userRepository.existsById(data.getId())){
                if (!userRepository.existsByEmail(data.getEmail())  || data.getEmail().equals(userRepository.getById(data.getId()).getEmail())){
                    User user = userRepository.getById(data.getId());
                    user.setEmail(data.getEmail());
                    user.setSurname(data.getSurname());
                    user.setFirstname(data.getFirstname());
                    user.setPhone(data.getPhone());
                    user.setCity(cityRepository.findById(data.getCityId()).orElseThrow(
                            () -> new ResourceNotFoundException("City","id",data.getCityId()) ));
                    userRepository.save(user);
                }else throw new AlreadyExistsException("User","email",data.getEmail());
            }else throw new ResourceNotFoundException("User","id");
        } else throw new NoRightException("delete","user");
    }

    public void deleteUserValidate(IdDTO data){
        if (userUtils.isAdmin() == 0 || userUtils.isAdmin() == data.getId()){
            if (userRepository.existsById(data.getId())){
                if(petRepository.existsPetByAdoptive_Id(data.getId())){
                    List<Pet> pets = petRepository.getAllByAdoptive_Id(data.getId());
                    for (Pet pet : pets){
                        petService.deletePetValidate(new IdDTO(pet.getId())); //Deletes the pet and all connected data, like matches and images
                    }
                }
            }else throw new ResourceNotFoundException("User","id");
        } else throw new NoRightException("delete","user");
    }

    @Transactional
    public void changePassword(PasswordChangeRequest data){
        User existingUser = userUtils.getRequestingUser();
        if (encoder.matches(data.getOldPassword(), existingUser.getPassword())){
            existingUser.setPassword(encoder.encode(data.getNewPassword()));
            userRepository.save(existingUser);
        } else throw new InvalidParameterException("Passwords doesn't match!");
    }


}
