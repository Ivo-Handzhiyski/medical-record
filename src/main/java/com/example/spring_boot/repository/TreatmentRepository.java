package com.example.spring_boot.repository;


import com.example.spring_boot.entity.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
    // Define custom query methods here if needed
}
