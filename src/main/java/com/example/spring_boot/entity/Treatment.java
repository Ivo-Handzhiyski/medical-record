package com.example.spring_boot.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "treatment")
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long treatmentID;

    @Column(length = 500)
    private String treatmentDescription;

    @OneToMany(mappedBy = "treatment", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Visit> visits = new ArrayList<>();

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

    public void addVisit(Visit visit) {
        visits.add(visit);
        visit.setTreatment(this);
    }

    public void removeVisit(Visit visit) {
        visits.remove(visit);
        visit.setTreatment(null);
    }
}