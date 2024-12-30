package com.example.spring_boot.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorID;

    @Column(nullable = false, length = 100)
    private String name;

    // Could store multiple specializations (comma-delimited) or consider a separate table
    @Column(length = 255)
    private String specializations;

    @Column(nullable = false)
    private boolean isPersonalDoctor;

    // One doctor can be the personal doctor for many patients
    @OneToMany(mappedBy = "personalDoctor")
    private List<Patient> patients;

    // Constructors
    public Doctor() {}

    public Doctor(String name, String specializations, boolean isPersonalDoctor) {
        this.name = name;
        this.specializations = specializations;
        this.isPersonalDoctor = isPersonalDoctor;
    }

    // Getters and Setters
    public Long getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(Long doctorID) {
        this.doctorID = doctorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecializations() {
        return specializations;
    }

    public void setSpecializations(String specializations) {
        this.specializations = specializations;
    }

    public boolean isPersonalDoctor() {
        return isPersonalDoctor;
    }

    public void setPersonalDoctor(boolean personalDoctor) {
        isPersonalDoctor = personalDoctor;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}