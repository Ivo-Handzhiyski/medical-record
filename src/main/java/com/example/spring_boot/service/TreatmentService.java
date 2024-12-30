package com.example.spring_boot.service;

import com.example.spring_boot.entity.Treatment;
import com.example.spring_boot.repository.TreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TreatmentService {

    @Autowired
    private TreatmentRepository treatmentRepository;

    public Treatment createTreatment(Treatment treatment) {
        return treatmentRepository.save(treatment);
    }

    public List<Treatment> getAllTreatments() {
        return treatmentRepository.findAll();
    }

    public Treatment getTreatmentById(Long id) {
        Optional<Treatment> treatmentOpt = treatmentRepository.findById(id);
        return treatmentOpt.orElse(null);
    }

    public Treatment updateTreatment(Long id, Treatment updatedTreatment) {
        Optional<Treatment> existingOpt = treatmentRepository.findById(id);
        if (existingOpt.isPresent()) {
            Treatment existing = existingOpt.get();
            // Update fields as needed
            existing.setTreatmentDescription(updatedTreatment.getTreatmentDescription());
            return treatmentRepository.save(existing);
        }
        return null;
    }

    public boolean deleteTreatment(Long id) {
        if (treatmentRepository.existsById(id)) {
            treatmentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
