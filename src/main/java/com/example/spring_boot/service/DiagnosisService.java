package com.example.spring_boot.service;

import com.example.spring_boot.entity.Diagnosis;
import com.example.spring_boot.repository.DiagnosisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiagnosisService {

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    public Diagnosis createDiagnosis(Diagnosis diagnosis) {
        return diagnosisRepository.save(diagnosis);
    }

    public List<Diagnosis> getAllDiagnoses() {
        return diagnosisRepository.findAll();
    }

    public Diagnosis getDiagnosisById(Long id) {
        Optional<Diagnosis> diagnosis = diagnosisRepository.findById(id);
        return diagnosis.orElse(null);
    }

    public Diagnosis updateDiagnosis(Long id, Diagnosis updatedDiagnosis) {
        Optional<Diagnosis> existingOpt = diagnosisRepository.findById(id);
        if (existingOpt.isPresent()) {
            Diagnosis existing = existingOpt.get();
            existing.setDiagnosisName(updatedDiagnosis.getDiagnosisName());
            return diagnosisRepository.save(existing);
        }
        return null;
    }

    public boolean deleteDiagnosis(Long id) {
        if (diagnosisRepository.existsById(id)) {
            diagnosisRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
