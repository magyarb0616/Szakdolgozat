package com.szakdolgozat.petadopt.Repository;

import com.szakdolgozat.petadopt.Model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    Boolean existsCityByName(String name);
    Boolean existsCityByCountry_Id(Long id);
    List<City> findAllByCountry_Id(Long id);

}
