package com.parcial.mutantes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parcial.mutantes.model.DnaEntity;
import com.parcial.mutantes.model.DnaRequest;
import com.parcial.mutantes.repository.DnaRepository;
import com.parcial.mutantes.service.MutantService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mutant")
public class MutantController {

    @Autowired
    private DnaRepository dnaRepository;

    @Autowired
    private MutantService mutantService;

    @PostMapping("/")
    public ResponseEntity<String> isMutant(@RequestBody DnaRequest dnaRequest) {
        String[] dna = dnaRequest.getDna();

        // Validar que el ADN solo contenga caracteres válidos
        for (String strand : dna) {
            if (!strand.matches("[ATCG]+")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid characters in DNA sequence");
            }
        }

        String dnaString = String.join("", dna);  // Convertir el array de ADN a una cadena 

        // Verificar si el ADN ya fue procesado
        if (dnaRepository.existsByDnaSequence(dnaString)) {
            return ResponseEntity.ok("ADN ya procesado");
        }

        boolean isMutant = mutantService.esMutante(dna);

        // Guardar en la base de datos
        DnaEntity dnaEntity = new DnaEntity();
        dnaEntity.setDnaSequence(dnaString);
        dnaEntity.setMutant(isMutant);
        dnaRepository.save(dnaEntity);

        // Retornar una respuesta dependiendo el caso
        return isMutant 
            ? ResponseEntity.ok("Mutant detected") 
            : ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not a mutant");
    }

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
