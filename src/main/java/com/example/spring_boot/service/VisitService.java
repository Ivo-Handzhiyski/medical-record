package com.example.spring_boot.service;

import com.example.spring_boot.entity.*;
import com.example.spring_boot.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VisitService {

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @Autowired
    private SickNoteRepository sickNoteRepository;

    @Autowired
    private TreatmentRepository treatmentRepository;

    public Visit createVisit(Long doctorId, Long patientId, Long diagnosisId, Long sickNoteId, Long treatmentId, LocalDateTime visitDate) {
        Visit visit = new Visit();
        visit.setVisitDate(visitDate);

        // Associate doctor
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        visit.setDoctor(doctor);

        // Associate patient
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        visit.setPatient(patient);

        // Associate optional diagnosis
        if (diagnosisId != null) {
            Diagnosis diagnosis = diagnosisRepository.findById(diagnosisId)
                    .orElseThrow(() -> new RuntimeException("Diagnosis not found"));
            visit.setDiagnosis(diagnosis);
        }

        // Associate optional sickNote
        if (sickNoteId != null) {
            SickNote sickNote = sickNoteRepository.findById(sickNoteId)
                    .orElseThrow(() -> new RuntimeException("Sick note not found"));
            visit.setSickNote(sickNote);
        }

        // Associate optional treatment
        if (treatmentId != null) {
            Treatment treatment = treatmentRepository.findById(treatmentId)
                    .orElseThrow(() -> new RuntimeException("Treatment not found"));
            visit.setTreatment(treatment);
        }

        return visitRepository.save(visit);
    }

    public List<Visit> getAllVisits() {
        return visitRepository.findAll();
    }

    public Visit getVisitById(Long id) {
        return visitRepository.findById(id).orElse(null);
    }

    public Visit updateVisit(Long visitId, Long doctorId, Long patientId, Long diagnosisId, Long sickNoteId, Long treatmentId, LocalDateTime visitDate) {
        Visit existingVisit = visitRepository.findById(visitId).orElse(null);
        if (existingVisit == null) {
            return null;
        }

        existingVisit.setVisitDate(visitDate);

        // Re-associate doctor
        if (doctorId != null) {
            Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
            existingVisit.setDoctor(doctor);
        }

        // Re-associate patient
        if (patientId != null) {
            Patient patient = patientRepository.findById(patientId).orElseThrow();
            existingVisit.setPatient(patient);
        }

        // Re-associate diagnosis
        if (diagnosisId != null) {
            Diagnosis diagnosis = diagnosisRepository.findById(diagnosisId).orElseThrow();
            existingVisit.setDiagnosis(diagnosis);
        } else {
            existingVisit.setDiagnosis(null);
        }

        // Re-associate sick note
        if (sickNoteId != null) {
            SickNote sickNote = sickNoteRepository.findById(sickNoteId).orElseThrow();
            existingVisit.setSickNote(sickNote);
        } else {
            existingVisit.setSickNote(null);
        }

        // Re-associate treatment
        if (treatmentId != null) {
            Treatment treatment = treatmentRepository.findById(treatmentId).orElseThrow();
            existingVisit.setTreatment(treatment);
        } else {
            existingVisit.setTreatment(null);
        }

        return visitRepository.save(existingVisit);
    }

    public boolean deleteVisit(Long id) {
        if (visitRepository.existsById(id)) {
            visitRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
