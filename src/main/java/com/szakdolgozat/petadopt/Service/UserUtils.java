package com.szakdolgozat.petadopt.Service;

import com.szakdolgozat.petadopt.Model.User;
import com.szakdolgozat.petadopt.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserUtils {
    @Autowired
    private UserRepository userRepository;

    public Long isAdmin(){
        //Checks if the requesting user has Admin role or not
        //for admins it gives back a 0, for users it gives back the user ID
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String roles = userDetails.getAuthorities().toString();
        if (roles.contains("ROLE_ADMIN")){
            return 0L;
        } else {
            return userRepository.getUserByUsername(userDetails.getUsername()).getId();
        }
    }

    public User getRequestingUser(){
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.getUserByUsername(userDetails.getUsername());
    }



}
