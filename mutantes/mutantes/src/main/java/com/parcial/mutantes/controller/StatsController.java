package com.parcial.mutantes.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parcial.mutantes.repository.DnaRepository;

@RestController
public class StatsController {

    @Autowired
    private DnaRepository dnaRepository;

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        long countMutantDna = dnaRepository.countByIsMutantTrue();
        long countHumanDna = dnaRepository.countByIsMutantFalse();
        double ratio = (countMutantDna + countHumanDna) > 0 ? (double) countMutantDna / (countMutantDna + countHumanDna) : 0.0;

        Map<String, Object> stats = new HashMap<>();
        stats.put("count_mutant_dna", countMutantDna);
        stats.put("count_human_dna", countHumanDna);
        stats.put("ratio", ratio);
        
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}
