package com.example.spring_boot.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "treatment")
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long treatmentID;

    @Column(length = 500)
    private String treatmentDescription;

    // Constructors
    public Treatment() {}

    public Treatment(String treatmentDescription) {
        this.treatmentDescription = treatmentDescription;
    }

    // Getters and Setters
    public Long getTreatmentID() {
        return treatmentID;
    }

    public void setTreatmentID(Long treatmentID) {
        this.treatmentID = treatmentID;
    }

    public String getTreatmentDescription() {
        return treatmentDescription;
    }

    public void setTreatmentDescription(String treatmentDescription) {
        this.treatmentDescription = treatmentDescription;
    }
}