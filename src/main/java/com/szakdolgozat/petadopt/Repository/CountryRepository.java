package com.szakdolgozat.petadopt.Repository;


import com.szakdolgozat.petadopt.Model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CountryRepository extends JpaRepository<Country, Long> {

}
