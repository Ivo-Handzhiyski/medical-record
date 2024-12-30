package com.example.spring_boot.service;

import com.example.spring_boot.entity.Patient;
import com.example.spring_boot.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        return patient.orElse(null);
    }

    public Patient updatePatient(Long id, Patient updatedPatient) {
        Optional<Patient> existingOpt = patientRepository.findById(id);
        if (existingOpt.isPresent()) {
            Patient existing = existingOpt.get();
            existing.setHasPaidInsuranceForLast6Months(updatedPatient.getHasPaidInsuranceForLast6Months());
            existing.setName(updatedPatient.getName());
            existing.setPersonalNumber(updatedPatient.getPersonalNumber());
            existing.setPersonalDoctorId(updatedPatient.getPersonalDoctorId());
            return patientRepository.save(existing);
        }
        return null;
    }

    public boolean deletePatient(Long id) {
        if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
