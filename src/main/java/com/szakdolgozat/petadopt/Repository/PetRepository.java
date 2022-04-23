package com.szakdolgozat.petadopt.Repository;


import com.szakdolgozat.petadopt.Model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    Boolean existsPetByCity_Id(Long id);
    Boolean existsPetByBreedId(Long id);
    Boolean existsPetByAdoptive_Id(Long id);
    Boolean existsPetById(Long id);
    List<Pet> getAllByAdoptive_Id(Long id);




}
