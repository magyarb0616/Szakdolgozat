package com.szakdolgozat.petadopt.Service;

import com.szakdolgozat.petadopt.DTO.SearchDTO;
import com.szakdolgozat.petadopt.Model.Pet;
import com.szakdolgozat.petadopt.Repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

@Autowired
private PetRepository petRepository;
@Autowired
private EntityManager entityManager;

public List<Pet> searchPet(SearchDTO data){
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Pet> criteriaQuery = criteriaBuilder.createQuery(Pet.class);
    Root<Pet> root = criteriaQuery.from(Pet.class);


    Long breedId = data.getBreedId();
    Integer sex = data.getSex();
    Integer size = data.getSize();
    Integer hair = data.getHair();
    Integer movement = data.getMovement();

    List<Predicate> searchCriterias = new ArrayList<>();

    if ( (breedId != 0)  && (breedId != null) ){
        searchCriterias.add( criteriaBuilder.equal( root.get("breed"),breedId ) );
    }

    if (sex == 1 || sex == 0){
        Boolean gender = null;
        if (sex == 1){
            gender = true;
        } else {
            gender = false;
        }
        searchCriterias.add( criteriaBuilder.equal( root.get("sex"),gender ) );
    }

    if( size>=1 && size<=3 ){
        searchCriterias.add( criteriaBuilder.equal( root.get("size"),size ) );
    }

    if( hair>=1 && hair<=3 ){
        searchCriterias.add( criteriaBuilder.equal( root.get("hair"),hair ) );
    }

    if( movement>=1 && movement<=3 ){
        searchCriterias.add( criteriaBuilder.equal( root.get("movement"),movement ) );
    }


    criteriaQuery.select( root ).where(criteriaBuilder.and(searchCriterias.toArray(new Predicate[searchCriterias.size()]) ));
    return entityManager.createQuery(criteriaQuery).getResultList();
}



}
