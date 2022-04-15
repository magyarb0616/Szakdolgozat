package com.szakdolgozat.petadopt.Repository;

import com.szakdolgozat.petadopt.Model.Breed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Long> {
    Boolean existsBreedById(Long id);
    Boolean existsBreedBySpecies_Id(Long id);
    Boolean existsBreedByName(String name);
    List<Breed> findAllBySpecies_Id(Long id);



}
