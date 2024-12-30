package com.example.spring_boot.controller;


import com.example.spring_boot.entity.Visit;
import com.example.spring_boot.dto.VisitRequest;
import com.example.spring_boot.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visits")
public class VisitController {

    @Autowired
    private VisitService visitService;

    // CREATE
    @PostMapping
    public ResponseEntity<Visit> createVisit(@RequestBody VisitRequest request) {
        Visit newVisit = visitService.createVisit(
                request.getDoctorId(),
                request.getPatientId(),
                request.getDiagnosisId(),
                request.getSickNoteId(),
                request.getTreatmentId(),
                request.getVisitDate()
        );
        return ResponseEntity.ok(newVisit);
    }

    // READ (all)
    @GetMapping
    public ResponseEntity<List<Visit>> getAllVisits() {
        List<Visit> visits = visitService.getAllVisits();
        return ResponseEntity.ok(visits);
    }

    // READ (one)
    @GetMapping("/{id}")
    public ResponseEntity<Visit> getVisitById(@PathVariable Long id) {
        Visit visit = visitService.getVisitById(id);
        if (visit == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(visit);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Visit> updateVisit(@PathVariable Long id, @RequestBody VisitRequest request) {
        Visit updatedVisit = visitService.updateVisit(
                id,
                request.getDoctorId(),
                request.getPatientId(),
                request.getDiagnosisId(),
                request.getSickNoteId(),
                request.getTreatmentId(),
                request.getVisitDate()
        );
        if (updatedVisit == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedVisit);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisit(@PathVariable Long id) {
        boolean deleted = visitService.deleteVisit(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}

