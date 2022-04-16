package com.szakdolgozat.petadopt.Repository;

import com.szakdolgozat.petadopt.Model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByPetIDId(Long id);
    Boolean existsImageByPath(String path);
    Boolean existsImageByPetID_Id(Long id);
    void deleteAllByPetID_Id(Long id);

}
