package com.example.spring_boot.service;

import com.example.spring_boot.entity.Doctor;
import com.example.spring_boot.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(Long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        return doctor.orElse(null);
    }

    public Doctor updateDoctor(Long id, Doctor updatedDoctor) {
        // If doctor does not exist, return null or throw an exception
        Optional<Doctor> existingDoctorOpt = doctorRepository.findById(id);
        if (existingDoctorOpt.isPresent()) {
            Doctor existingDoctor = existingDoctorOpt.get();
            existingDoctor.setPersonalDoctor(updatedDoctor.getPersonalDoctor());
            existingDoctor.setName(updatedDoctor.getName());
            // If there's a many-to-many relationship for specializations,
            // you would handle that logic here.
            return doctorRepository.save(existingDoctor);
        }
        return null;
    }

    public boolean deleteDoctor(Long id) {
        if (doctorRepository.existsById(id)) {
            doctorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}