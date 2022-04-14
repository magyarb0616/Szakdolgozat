package com.szakdolgozat.petadopt.Repository;


import com.szakdolgozat.petadopt.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsUserByCity_Id(Long id);


}
