package com.example.spring_boot.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "diagnosis")
public class Diagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diagnosisID;

    @Column(nullable = false, length = 255)
    private String diagnosisName;

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
}