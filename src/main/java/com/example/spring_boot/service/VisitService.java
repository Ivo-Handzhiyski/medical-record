package com.example.spring_boot.service;

import com.example.spring_boot.entity.*;
import com.example.spring_boot.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    @Autowired
    private UserService userService; // needed to find current user’s doctor/patient IDs

    /**
     * CREATE: only doctors or admins can create visits.
     * If doctor, must be the same doctor as doctorId param.
     */
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public Visit createVisit(Long doctorId,
                             Long patientId,
                             Long diagnosisId,
                             Long sickNoteId,
                             Long treatmentId,
                             LocalDateTime visitDate) {
        // If the current user is a doctor, ensure they match doctorId
        if (isDoctor()) {
            Long currentDoctorId = getCurrentUserDoctorId();
            if (!doctorId.equals(currentDoctorId)) {
                throw new AccessDeniedException("You can only create visits for yourself.");
            }
        }

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

    /**
     * READ (ALL):
     * Patients see only their own visits;
     * Doctors and admins see all visits.
     */
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','PATIENT')")
    public List<Visit> getAllVisits() {
        if (isPatient()) {
            Long currentPatientId = getCurrentUserPatientId();
            // Return only those visits for this patient
            return visitRepository.findAll().stream()
                    .filter(v -> v.getPatient().getPatientID().equals(currentPatientId))
                    .toList();
        }
        // if doctor or admin
        return visitRepository.findAll();
    }

    /**
     * READ (ONE):
     * Patients see only their own;
     * Doctors and admins see any.
     */
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','PATIENT')")
    public Visit getVisitById(Long id) {
        Visit visit = visitRepository.findById(id).orElse(null);
        if (visit == null) return null;

        if (isPatient()) {
            Long currentPatientId = getCurrentUserPatientId();
            if (!visit.getPatient().getPatientID().equals(currentPatientId)) {
                throw new AccessDeniedException("Not allowed to view another patient’s visit.");
            }
        }
        return visit;
    }

    /**
     * UPDATE:
     * Only doctors or admins can update visits.
     * If doctor, they must be the same doctor for the existing visit.
     */
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public Visit updateVisit(Long visitId,
                             Long doctorId,
                             Long patientId,
                             Long diagnosisId,
                             Long sickNoteId,
                             Long treatmentId,
                             LocalDateTime visitDate) {

        Visit existingVisit = visitRepository.findById(visitId).orElse(null);
        if (existingVisit == null) {
            return null;
        }

        // If doctor, ensure they match the existing visit's doctor
        if (isDoctor()) {
            Long currentDoctorId = getCurrentUserDoctorId();
            if (!existingVisit.getDoctor().getDoctorID().equals(currentDoctorId)) {
                throw new AccessDeniedException("Cannot update a visit not performed by you.");
            }
        }

        // Re-associate fields
        existingVisit.setVisitDate(visitDate);

        if (doctorId != null) {
            Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
            existingVisit.setDoctor(doctor);
        }

        if (patientId != null) {
            Patient patient = patientRepository.findById(patientId).orElseThrow();
            existingVisit.setPatient(patient);
        }

        if (diagnosisId != null) {
            Diagnosis diagnosis = diagnosisRepository.findById(diagnosisId).orElseThrow();
            existingVisit.setDiagnosis(diagnosis);
        } else {
            existingVisit.setDiagnosis(null);
        }

        if (sickNoteId != null) {
            SickNote sickNote = sickNoteRepository.findById(sickNoteId).orElseThrow();
            existingVisit.setSickNote(sickNote);
        } else {
            existingVisit.setSickNote(null);
        }

        if (treatmentId != null) {
            Treatment treatment = treatmentRepository.findById(treatmentId).orElseThrow();
            existingVisit.setTreatment(treatment);
        } else {
            existingVisit.setTreatment(null);
        }

        return visitRepository.save(existingVisit);
    }

    /**
     * DELETE:
     * Only admins can delete.
     */
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteVisit(Long id) {
        if (visitRepository.existsById(id)) {
            visitRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // -------------------------------------------------------------
    // Helper methods to check roles and get the current user context
    // -------------------------------------------------------------

    private boolean isPatient() {
        return hasRole("ROLE_PATIENT");
    }

    private boolean isDoctor() {
        return hasRole("ROLE_DOCTOR");
    }

    private boolean hasRole(String role) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals(role));
    }

    private Long getCurrentUserPatientId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.findByUsername(username);
        if (currentUser != null && currentUser.getPatient() != null) {
            return currentUser.getPatient().getPatientID();
        }
        return null;
    }

    private Long getCurrentUserDoctorId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.findByUsername(username);
        if (currentUser != null && currentUser.getDoctor() != null) {
            return currentUser.getDoctor().getDoctorID();
        }
        return null;
    }
}
