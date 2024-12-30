package com.example.spring_boot.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patientid")
    private Long patientID;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 20)
    private String personalNumber; // e.g., SSN or national ID

    @Column(name = "has_paid_insurance_for_last6months", nullable = false)
    private boolean hasPaidInsuranceForLast6Months;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_doctorid")
    private Doctor personalDoctor;

    // One patient can have many visits
    @OneToMany(mappedBy = "patient")
    private List<Visit> visits;

    // Constructors
    public Patient() {}

    public Patient(String name, String personalNumber, boolean hasPaidInsuranceForLast6Months, Doctor personalDoctor) {
        this.name = name;
        this.personalNumber = personalNumber;
        this.hasPaidInsuranceForLast6Months = hasPaidInsuranceForLast6Months;
        this.personalDoctor = personalDoctor;
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

    public boolean getHasPaidInsuranceForLast6Months() {
        return hasPaidInsuranceForLast6Months;
    }

    public void setHasPaidInsuranceForLast6Months(boolean hasPaidInsuranceForLast6Months) {
        this.hasPaidInsuranceForLast6Months = hasPaidInsuranceForLast6Months;
    }

    public Doctor getPersonalDoctorId() {
        return personalDoctor;
    }

    public void setPersonalDoctorId(Doctor personalDoctor) {
        this.personalDoctor = personalDoctor;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    public void addVisit(Visit visit) {
        visits.add(visit);
        visit.setPatient(this);
    }

    public void removeVisit(Visit visit) {
        visits.remove(visit);
        visit.setPatient(null);
    }
}