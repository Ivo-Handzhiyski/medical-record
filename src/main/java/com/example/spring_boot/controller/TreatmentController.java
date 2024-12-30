package com.example.spring_boot.controller;

import com.example.spring_boot.entity.Treatment;
import com.example.spring_boot.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/treatments")
public class TreatmentController {

    @Autowired
    private TreatmentService treatmentService;

    // CREATE
    @PostMapping
    public ResponseEntity<Treatment> createTreatment(@RequestBody Treatment treatment) {
        Treatment savedTreatment = treatmentService.createTreatment(treatment);
        return ResponseEntity.ok(savedTreatment);
    }

    // READ (all)
    @GetMapping
    public ResponseEntity<List<Treatment>> getAllTreatments() {
        List<Treatment> treatments = treatmentService.getAllTreatments();
        return ResponseEntity.ok(treatments);
    }

    // READ (one)
    @GetMapping("/{id}")
    public ResponseEntity<Treatment> getTreatmentById(@PathVariable Long id) {
        Treatment treatment = treatmentService.getTreatmentById(id);
        if (treatment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(treatment);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Treatment> updateTreatment(@PathVariable Long id,
                                                     @RequestBody Treatment updatedTreatment) {
        Treatment treatment = treatmentService.updateTreatment(id, updatedTreatment);
        if (treatment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(treatment);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTreatment(@PathVariable Long id) {
        boolean deleted = treatmentService.deleteTreatment(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}

