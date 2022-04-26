package com.szakdolgozat.petadopt.Controller;


import com.szakdolgozat.petadopt.DTO.IdDTO;
import com.szakdolgozat.petadopt.DTO.Security.Request.PasswordChangeRequest;
import com.szakdolgozat.petadopt.DTO.Security.Response.MessageResponse;
import com.szakdolgozat.petadopt.DTO.UserUpdateDTO;
import com.szakdolgozat.petadopt.Exception.ResourceNotFoundException;
import com.szakdolgozat.petadopt.Model.User;
import com.szakdolgozat.petadopt.Repository.UserRepository;
import com.szakdolgozat.petadopt.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Map<String, String>> getAllUser(){
        List<User> users = userRepository.findAll();
        List<Map<String, String>> out = new ArrayList<>();

        for (User user : users){
            String id = user.getId().toString();
            String username = user.getUsername();
            String password = user.getPassword();
            String surname = user.getSurname();
            String firstname = user.getFirstname();
            String email = user.getEmail();
            String phone = user.getPhone();
            String cityId = user.getCity().getId().toString();
            String cityName = user.getCity().getName();

            Map<String, String> map = new LinkedHashMap<>();

            map.put("id",id);
            map.put("username",username);
            map.put("password",password);
            map.put("surname", surname);
            map.put("firstname",firstname);
            map.put("email",email);
            map.put("phone",phone);
            map.put("cityId",cityId);
            map.put("cityName",cityName);
            out.add(map);
        }
        return out;
    }

    @GetMapping
    public Map<String, String> getUserById(IdDTO data){
        User existingUser = userRepository.findById(data.getId()).orElseThrow(
                () -> new ResourceNotFoundException("User","id"));

        Map<String, String> map = new LinkedHashMap<>();

        String id = existingUser.getId().toString();
        String username = existingUser.getUsername();
        String surname = existingUser.getSurname();
        String firstname = existingUser.getFirstname();
        String email = existingUser.getEmail();
        String phone = existingUser.getPhone();
        String cityId = existingUser.getCity().getId().toString();
        String cityName = existingUser.getCity().getName();
        String countryName = existingUser.getCity().getCountry().getName();
        String countryId = existingUser.getCity().getCountry().getId().toString();

        map.put("id",id);
        map.put("username",username);
        map.put("surname", surname);
        map.put("firstname",firstname);
        map.put("email",email);
        map.put("phone",phone);
        map.put("cityId",cityId);
        map.put("cityName",cityName);
        map.put("countryName",countryName);
        map.put("countryId", countryId);

        return map;
    }

    @PostMapping(path = "/update")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDTO data){
        //System.out.println("id: "+data.getId()+", city: "+data.getCityId()+" email:"+data.getEmail());
        userService.updateUserValidate(data);
        return ResponseEntity.ok(new MessageResponse("User successfully updated!"));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(IdDTO data){
        userService.deleteUserValidate(data);
        return ResponseEntity.ok(new MessageResponse("User successfully deleted!"));
    }

    @PostMapping(path = "/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest data){
        userService.changePassword(data);
        return ResponseEntity.ok("Password changed successfully!");
    }


}
