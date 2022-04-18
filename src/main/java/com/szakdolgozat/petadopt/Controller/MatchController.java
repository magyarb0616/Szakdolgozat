package com.szakdolgozat.petadopt.Controller;

import com.szakdolgozat.petadopt.DTO.IdDTO;
import com.szakdolgozat.petadopt.Exception.NoRightException;
import com.szakdolgozat.petadopt.Exception.ResourceNotFoundException;
import com.szakdolgozat.petadopt.Model.Match;
import com.szakdolgozat.petadopt.Repository.MatchRepository;
import com.szakdolgozat.petadopt.Service.MatchService;
import com.szakdolgozat.petadopt.Service.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private UserUtils userUtils;

    @GetMapping
    public List<Map<String, String>> getAdopterMatches(IdDTO data){
        if (userUtils.isAdmin() == 0 || userUtils.isAdmin() == data.getId()){
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
        } else { throw new NoRightException("List","matches"); }

    }

    @PostMapping()
    public ResponseEntity<?> createMatch(IdDTO data){
        matchService.createMatchValidate(data);
        return ResponseEntity.ok("Match successfully created!");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMatch(IdDTO data){
        if (matchRepository.existsById(data.getId())){
            if (userUtils.isAdmin() == 0 || userUtils.isAdmin() == matchRepository.getById(data.getId()).getAdopterID().getId()){
                matchRepository.deleteById(data.getId());
            } else { throw new NoRightException("delete","match"); }
        } else { throw new ResourceNotFoundException("Match","id",data.getId()); }
        return ResponseEntity.ok("Delete successfull!");
    }

}