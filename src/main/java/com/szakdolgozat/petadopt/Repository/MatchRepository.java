package com.szakdolgozat.petadopt.Repository;

import com.szakdolgozat.petadopt.Model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findAllByAdopterID_Id(Long id);


}
