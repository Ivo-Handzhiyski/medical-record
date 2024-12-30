package com.example.spring_boot.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientID;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 20)
    private String personalNumber; // e.g., SSN or national ID

    @Column(nullable = false)
    private boolean hasPaidInsuranceForLast6Months;

    // Each patient has exactly one personal (primary care) doctor
    @ManyToOne
    @JoinColumn(name = "personalDoctorID")
    private Doctor personalDoctor;

    // One patient can have many visits
    @OneToMany(mappedBy = "patient")
    private List<Visit> visits;

    // Constructors
    public Patient() {}

    public Patient(String name, String personalNumber, boolean hasPaidInsuranceForLast6Months) {
        this.name = name;
        this.personalNumber = personalNumber;
        this.hasPaidInsuranceForLast6Months = hasPaidInsuranceForLast6Months;
    }

    // Getters and Setters
    public Long getPatientID() {
        return patientID;
    }

    public void setPatientID(Long patientID) {
        this.patientID = patientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public boolean isHasPaidInsuranceForLast6Months() {
        return hasPaidInsuranceForLast6Months;
    }

    public void setHasPaidInsuranceForLast6Months(boolean hasPaidInsuranceForLast6Months) {
        this.hasPaidInsuranceForLast6Months = hasPaidInsuranceForLast6Months;
    }

    public Doctor getPersonalDoctor() {
        return personalDoctor;
    }

    public void setPersonalDoctor(Doctor personalDoctor) {
        this.personalDoctor = personalDoctor;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }
}