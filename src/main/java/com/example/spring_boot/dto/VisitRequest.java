package com.example.spring_boot.dto;

import java.time.LocalDateTime;

public class VisitRequest {

    private Long doctorId;
    private Long patientId;
    private Long diagnosisId;
    private Long sickNoteId;
    private Long treatmentId;
    private LocalDateTime visitDate;

    public VisitRequest() {
    }

    public VisitRequest(Long doctorId,
                        Long patientId,
                        Long diagnosisId,
                        Long sickNoteId,
                        Long treatmentId,
                        LocalDateTime visitDate) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.diagnosisId = diagnosisId;
        this.sickNoteId = sickNoteId;
        this.treatmentId = treatmentId;
        this.visitDate = visitDate;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(Long diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public Long getSickNoteId() {
        return sickNoteId;
    }

    public void setSickNoteId(Long sickNoteId) {
        this.sickNoteId = sickNoteId;
    }

    public Long getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(Long treatmentId) {
        this.treatmentId = treatmentId;
    }

    public LocalDateTime getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDateTime visitDate) {
        this.visitDate = visitDate;
    }
}
