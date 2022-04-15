package com.szakdolgozat.petadopt.Controller;

import com.szakdolgozat.petadopt.DTO.IdDTO;
import com.szakdolgozat.petadopt.DTO.MatchDTO;
import com.szakdolgozat.petadopt.Exception.ResourceNotFoundException;
import com.szakdolgozat.petadopt.Model.Match;
import com.szakdolgozat.petadopt.Repository.MatchRepository;
import com.szakdolgozat.petadopt.Service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/match")
public class MatchController {
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private MatchService matchService;

    @GetMapping
    public List<Map<String, String>> getAdopterMatches(IdDTO data){
    List<Match> matches = matchRepository.findAllByAdopterID_Id(data.getId());
    List<Map<String, String>> out = new ArrayList<>();
    if (matches.isEmpty()){ return out; } else {
        //TODO send back an entire pet when ready to reduce calls
        for (Match match : matches){
            String id = match.getId().toString();
            String petId = match.getPetID().toString();
            String adopterId = match.getAdopterID().toString();
            Map<String, String> map = new LinkedHashMap<>();
            map.put("id",id);
            map.put("petId",petId);
            map.put("adopterId",adopterId);
            out.add(map);
        }
    }
    return out;
    }

    @PostMapping()
    public ResponseEntity<?> createMatch(MatchDTO data){
        matchService.createMatchValidate(data);
        //TODO increment pet score when a match created
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMatch(IdDTO data){
        if (matchRepository.existsById(data.getId())){
            matchRepository.deleteById(data.getId());
        } else { throw new ResourceNotFoundException("Match","id",data.getId()); }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}