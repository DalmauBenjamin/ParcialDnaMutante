package com.parcial.mutantes.model;

import jakarta.persistence.*;

@Entity
@Table(name = "dna")
public class DnaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String dnaSequence;

    @Column(nullable = false)
    private boolean isMutant;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDnaSequence() {
        return dnaSequence;
    }

    public void setDnaSequence(String dnaSequence) {
        this.dnaSequence = dnaSequence;
    }

    public boolean isMutant() {
        return isMutant;
    }

    public void setMutant(boolean isMutant) {
        this.isMutant = isMutant;
    }
}
