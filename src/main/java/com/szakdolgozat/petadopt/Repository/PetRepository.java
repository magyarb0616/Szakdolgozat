package com.szakdolgozat.petadopt.Repository;


import com.szakdolgozat.petadopt.Model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    Boolean existsPetByCity_Id(Long id);
    Boolean existsPetByBreedId(Long id);



}
