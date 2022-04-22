package com.szakdolgozat.petadopt.Controller;

import com.szakdolgozat.petadopt.DTO.Security.Request.LoginRequest;
import com.szakdolgozat.petadopt.DTO.Security.Request.SignupRequest;
import com.szakdolgozat.petadopt.DTO.Security.Response.JwtResponse;
import com.szakdolgozat.petadopt.DTO.Security.Response.MessageResponse;
import com.szakdolgozat.petadopt.Model.ERole;
import com.szakdolgozat.petadopt.Model.Role;
import com.szakdolgozat.petadopt.Model.User;
import com.szakdolgozat.petadopt.Repository.CityRepository;
import com.szakdolgozat.petadopt.Repository.RoleRepository;
import com.szakdolgozat.petadopt.Repository.UserRepository;
import com.szakdolgozat.petadopt.Security.JwtUtils;
import com.szakdolgozat.petadopt.Security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/auth")
public class AuthorizationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtUtils jwtUtils;


    @PostMapping(path = "/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        System.out.println("JWT Token:"+jwt);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getUser().getId(),
                userDetails.getUsername(),
                userDetails.getUser().getEmail(),
                roles));
    }

    @PostMapping(path = "/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Username is already taken!"));
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Email is already in use!"));
        }
        // Create new user's account
        User user = new User(
                signupRequest.getUsername(),
                signupRequest.getSurname(),
                signupRequest.getFirstname(),
                signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()),
                signupRequest.getPhone(),
                cityRepository.getById(signupRequest.getCityId()) );



        Set<String> strRoles = signupRequest.getRole();
        //System.out.println("role:"+strRoles);
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });

        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
