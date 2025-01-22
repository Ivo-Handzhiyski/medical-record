package com.example.spring_boot.controller;
import com.example.spring_boot.entity.Visit;
import com.example.spring_boot.service.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/app/visits")
public class VisitViewController {

    private final VisitService visitService;

    public VisitViewController(VisitService visitService) {
        this.visitService = visitService;
    }

    // ------------------------------
    // LIST ALL VISITS
    // ------------------------------
    @GetMapping
    public String listVisits(Model model) {
        model.addAttribute("visits", visitService.getAllVisits());
        return "visit-list";  // e.g. /templates/visit-list.html
    }

    // ------------------------------
    // CREATE (SHOW FORM)
    // ------------------------------
    @GetMapping("/new")
    public String showVisitCreateForm(Model model) {
        // For a "new" visit, no existing data:
        model.addAttribute("visitId", null);
        model.addAttribute("doctorId", null);
        model.addAttribute("patientId", null);
        model.addAttribute("diagnosisId", null);
        model.addAttribute("sickNoteId", null);
        model.addAttribute("treatmentId", null);
        // Default to "now" or let user fill the date/time in the form
        model.addAttribute("visitDate", "");

        return "visit-form"; // e.g. /templates/visit-form.html
    }

    // ------------------------------
    // CREATE (HANDLE FORM SUBMIT)
    // ------------------------------
    @PostMapping
    public String createVisit(
            @RequestParam("doctorId") Long doctorId,
            @RequestParam("patientId") Long patientId,
            @RequestParam(value = "diagnosisId", required = false) Long diagnosisId,
            @RequestParam(value = "sickNoteId", required = false) Long sickNoteId,
            @RequestParam(value = "treatmentId", required = false) Long treatmentId,
            @RequestParam("visitDate") String visitDateStr
    ) {
        // Parse the date/time from the string (if not empty)
        LocalDateTime visitDate = null;
        if (visitDateStr != null && !visitDateStr.trim().isEmpty()) {
            // Expecting an ISO-like string, e.g. "2025-01-01T10:30"
            visitDate = LocalDateTime.parse(visitDateStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }

        visitService.createVisit(doctorId, patientId, diagnosisId, sickNoteId, treatmentId, visitDate);
        return "redirect:/app/visits";
    }

    // ------------------------------
    // EDIT (SHOW FORM)
    // ------------------------------
    @GetMapping("/edit/{id}")
    public String showVisitEditForm(@PathVariable("id") Long id, Model model) {
        Visit existing = visitService.getVisitById(id);
        if (existing == null) {
            return "redirect:/app/visits";
        }

        // Populate the model with existing data for the form
        model.addAttribute("visitId", existing.getVisitId());
        model.addAttribute("doctorId", (existing.getDoctor() != null) ? existing.getDoctor().getDoctorID() : null);
        model.addAttribute("patientId", (existing.getPatient() != null) ? existing.getPatient().getPatientID() : null);
        model.addAttribute("diagnosisId", (existing.getDiagnosis() != null) ? existing.getDiagnosis().getDiagnosisID() : null);
        model.addAttribute("sickNoteId", (existing.getSickNote() != null) ? existing.getSickNote().getSickNoteID() : null);
        model.addAttribute("treatmentId", (existing.getTreatment() != null) ? existing.getTreatment().getTreatmentID() : null);

        // Format the date/time for an HTML <input type="datetime-local"> if you want
        LocalDateTime visitDate = existing.getVisitDate();
        String visitDateStr = (visitDate != null)
                ? visitDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                : "";
        model.addAttribute("visitDate", visitDateStr);

        return "visit-form"; // reuse the same form for editing
    }

    // ------------------------------
    // EDIT (HANDLE FORM SUBMIT)
    // ------------------------------
    @PostMapping("/update/{id}")
    public String updateVisit(
            @PathVariable("id") Long visitId,
            @RequestParam("doctorId") Long doctorId,
            @RequestParam("patientId") Long patientId,
            @RequestParam(value = "diagnosisId", required = false) Long diagnosisId,
            @RequestParam(value = "sickNoteId", required = false) Long sickNoteId,
            @RequestParam(value = "treatmentId", required = false) Long treatmentId,
            @RequestParam("visitDate") String visitDateStr
    ) {
        LocalDateTime visitDate = null;
        if (visitDateStr != null && !visitDateStr.trim().isEmpty()) {
            visitDate = LocalDateTime.parse(visitDateStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }

        visitService.updateVisit(visitId, doctorId, patientId, diagnosisId, sickNoteId, treatmentId, visitDate);
        return "redirect:/app/visits";
    }

    // ------------------------------
    // DELETE
    // ------------------------------
    @GetMapping("/delete/{id}")
    public String deleteVisit(@PathVariable("id") Long id) {
        visitService.deleteVisit(id);
        return "redirect:/app/visits";
    }
}