package com.parcial.mutantes.repository;

import com.parcial.mutantes.model.DnaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DnaRepository extends JpaRepository<DnaEntity, Long> {
    boolean existsByDnaSequence(String dnaSequence);
    long countByIsMutantTrue();
    long countByIsMutantFalse();
}
