package com.example.spring_boot.controller;

import com.example.spring_boot.entity.Diagnosis;
import com.example.spring_boot.service.DiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diagnoses")
public class DiagnosisController {

    @Autowired
    private DiagnosisService diagnosisService;

    @PostMapping
    public ResponseEntity<Diagnosis> createDiagnosis(@RequestBody Diagnosis diagnosis) {
        Diagnosis savedDiagnosis = diagnosisService.createDiagnosis(diagnosis);
        return ResponseEntity.ok(savedDiagnosis);
    }

    @GetMapping
    public ResponseEntity<List<Diagnosis>> getAllDiagnoses() {
        List<Diagnosis> diagnoses = diagnosisService.getAllDiagnoses();
        return ResponseEntity.ok(diagnoses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Diagnosis> getDiagnosisById(@PathVariable Long id) {
        Diagnosis diagnosis = diagnosisService.getDiagnosisById(id);
        if (diagnosis == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(diagnosis);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Diagnosis> updateDiagnosis(@PathVariable Long id, @RequestBody Diagnosis updatedDiagnosis) {
        Diagnosis diagnosis = diagnosisService.updateDiagnosis(id, updatedDiagnosis);
        if (diagnosis == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(diagnosis);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiagnosis(@PathVariable Long id) {
        boolean deleted = diagnosisService.deleteDiagnosis(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
