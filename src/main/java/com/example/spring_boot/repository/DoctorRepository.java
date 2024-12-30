package com.example.spring_boot.repository;


import com.example.spring_boot.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    // You can define custom queries if needed
}
