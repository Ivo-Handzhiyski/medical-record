package com.example.spring_boot.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "diagnosis")
public class Diagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diagnosisID;

    @Column(nullable = false, length = 255)
    private String diagnosisName;

    @OneToMany(mappedBy = "diagnosis", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Visit> visits = new ArrayList<>();

    // Constructors
    public Diagnosis() {}

    public Diagnosis(String diagnosisName) {
        this.diagnosisName = diagnosisName;
    }

    // Getters and Setters
    public Long getDiagnosisID() {
        return diagnosisID;
    }

    public void setDiagnosisID(Long diagnosisID) {
        this.diagnosisID = diagnosisID;
    }

    public String getDiagnosisName() {
        return diagnosisName;
    }

    public void setDiagnosisName(String diagnosisName) {
        this.diagnosisName = diagnosisName;
    }

    public void addVisit(Visit visit) {
        visits.add(visit);
        visit.setDiagnosis(this);
    }

    public void removeVisit(Visit visit) {
        visits.remove(visit);
        visit.setDiagnosis(null);
    }
}