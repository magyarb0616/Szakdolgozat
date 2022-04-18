package com.szakdolgozat.petadopt.Repository;

import com.szakdolgozat.petadopt.Model.ERole;
import com.szakdolgozat.petadopt.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Boolean existsByName(String name);
    Optional<Role> findByName(ERole name);



}
