package com.example.spring_boot.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visit")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visitid")
    private Long visitId;

    @Column(name = "visit_date", nullable = false)
    private LocalDateTime visitDate;

    /**
     * Many visits can be associated with the same doctor.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctorid", referencedColumnName = "doctorid", nullable = false)
    private Doctor doctor;

    /**
     * Many visits can be associated with the same patient.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patientid", referencedColumnName = "patientid", nullable = false)
    private Patient patient;

    /**
     * Optional relationship to a Diagnosis (one diagnosis can be linked to many visits).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnosisid", referencedColumnName = "diagnosisid")
    private Diagnosis diagnosis;

    /**
     * Optional relationship to a SickNote (one sick note can be used for many visits if desired).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sicknoteid", referencedColumnName = "sicknoteid")
    private SickNote sickNote;

    /**
     * Optional relationship to a Treatment (one treatment can be linked to many visits if desired).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "treatmentid", referencedColumnName = "treatmentid")
    private Treatment treatment;

    // Constructors

    public Visit() {
    }

    public Visit(LocalDateTime visitDate,
                 Doctor doctor,
                 Patient patient,
                 Diagnosis diagnosis,
                 SickNote sickNote,
                 Treatment treatment) {
        this.visitDate = visitDate;
        this.doctor = doctor;
        this.patient = patient;
        this.diagnosis = diagnosis;
        this.sickNote = sickNote;
        this.treatment = treatment;
    }

    // Getters and Setters

    public Long getVisitId() {
        return visitId;
    }

    public void setVisitId(Long visitId) {
        this.visitId = visitId;
    }

    public LocalDateTime getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDateTime visitDate) {
        this.visitDate = visitDate;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }

    public SickNote getSickNote() {
        return sickNote;
    }

    public void setSickNote(SickNote sickNote) {
        this.sickNote = sickNote;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "visitId=" + visitId +
                ", visitDate=" + visitDate +
                '}';
    }
}
