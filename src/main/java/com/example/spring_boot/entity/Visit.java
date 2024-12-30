package com.example.spring_boot.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visit")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long visitID;

    @ManyToOne
    @JoinColumn(name = "patientID", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctorID", nullable = false)
    private Doctor doctor;

    @Column(nullable = false)
    private LocalDateTime visitDate;

    @ManyToOne
    @JoinColumn(name = "diagnosisID")
    private Diagnosis diagnosis;

    @ManyToOne
    @JoinColumn(name = "treatmentID")
    private Treatment treatment;

    @OneToOne
    @JoinColumn(name = "sickNoteID")
    private SickNote sickNote;

    // Constructors
    public Visit() {}

    public Visit(Patient patient, Doctor doctor, LocalDateTime visitDate) {
        this.patient = patient;
        this.doctor = doctor;
        this.visitDate = visitDate;
    }

    // Getters and Setters
    public Long getVisitID() {
        return visitID;
    }

    public void setVisitID(Long visitID) {
        this.visitID = visitID;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDateTime getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDateTime visitDate) {
        this.visitDate = visitDate;
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public SickNote getSickNote() {
        return sickNote;
    }

    public void setSickNote(SickNote sickNote) {
        this.sickNote = sickNote;
    }
}
