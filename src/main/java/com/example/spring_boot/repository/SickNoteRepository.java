package com.example.spring_boot.repository;

import com.example.spring_boot.entity.SickNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SickNoteRepository extends JpaRepository<SickNote, Long> {
}
