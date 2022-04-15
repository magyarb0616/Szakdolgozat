package com.szakdolgozat.petadopt.Repository;


import com.szakdolgozat.petadopt.Model.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeciesRepository extends JpaRepository<Species, Long> {

    Boolean existsSpeciesByName(String name);
    Boolean existsSpeciesById(Long id);

}
