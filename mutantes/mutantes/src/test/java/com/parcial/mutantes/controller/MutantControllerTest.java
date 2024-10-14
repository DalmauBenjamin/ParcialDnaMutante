package com.parcial.mutantes.controller;

import com.parcial.mutantes.model.DnaRequest;
import com.parcial.mutantes.repository.DnaRepository;
import com.parcial.mutantes.service.MutantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MutantControllerTest {

    @InjectMocks
    private MutantController mutantController;

    @Mock
    private MutantService mutantService;

    @Mock
    private DnaRepository dnaRepository; // Agregar DnaRepository como mock

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIsMutant_WhenMutant_DetectsMutant() {
        // Arrange
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        DnaRequest dnaRequest = new DnaRequest();
        dnaRequest.setDna(dna);
        
        when(mutantService.esMutante(dna)).thenReturn(true);  // Simula que el ADN es mutante.
        when(dnaRepository.existsByDnaSequence("ATGCGA...")).thenReturn(false); // Cambia por la cadena correcta

        // Act
        ResponseEntity<String> response = mutantController.isMutant(dnaRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Mutant detected", response.getBody());
    }

    @Test
    public void testIsMutant_WhenNotMutant_DetectsNotMutant() {
        // Arrange
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGATAG", "CCCTGA", "TCACTG"};
        DnaRequest dnaRequest = new DnaRequest();
        dnaRequest.setDna(dna);
        
        when(mutantService.esMutante(dna)).thenReturn(false);  // Simula que el ADN no es mutante.
        when(dnaRepository.existsByDnaSequence("ATGCGA...")).thenReturn(false); // Cambia por la cadena correcta

        // Act
        ResponseEntity<String> response = mutantController.isMutant(dnaRequest);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Not a mutant", response.getBody());
    }

    @Test
    public void testIsMutant_InvalidCharacters() {
        // Arrange
        String[] dna = {"ATGCGZ", "CAGTGC", "TTATGT", "AGATAG", "CCCTGA", "TCACTG"};
        DnaRequest dnaRequest = new DnaRequest();
        dnaRequest.setDna(dna);

        // Act
        ResponseEntity<String> response = mutantController.isMutant(dnaRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid characters in DNA sequence", response.getBody());
    }
}
