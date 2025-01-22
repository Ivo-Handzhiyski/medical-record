package com.example.spring_boot.controller;

import com.example.spring_boot.entity.Diagnosis;
import com.example.spring_boot.service.DiagnosisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/app/diagnoses")
public class DiagnosisViewController {

    private final DiagnosisService diagnosisService;

    public DiagnosisViewController(DiagnosisService diagnosisService) {
        this.diagnosisService = diagnosisService;
    }

    // 1. List all diagnoses
    @GetMapping
    public String listDiagnoses(Model model) {
        model.addAttribute("diagnoses", diagnosisService.getAllDiagnoses());
        return "diagnosis-list"; // e.g. /templates/diagnosis-list.html
    }

    // 2. Show form to create new diagnosis
    @GetMapping("/new")
    public String showDiagnosisCreateForm(Model model) {
        model.addAttribute("diagnosis", new Diagnosis());
        return "diagnosis-form"; // e.g. /templates/diagnosis-form.html
    }

    // 3. Create new diagnosis
    @PostMapping
    public String createDiagnosis(@ModelAttribute("diagnosis") Diagnosis diagnosis) {
        diagnosisService.createDiagnosis(diagnosis);
        return "redirect:/app/diagnoses";
    }

    // 4. Show form to edit existing diagnosis
    @GetMapping("/edit/{id}")
    public String showDiagnosisEditForm(@PathVariable Long id, Model model) {
        Diagnosis diagnosis = diagnosisService.getDiagnosisById(id);
        if (diagnosis == null) {
            return "redirect:/app/diagnoses";
        }
        model.addAttribute("diagnosis", diagnosis);
        return "diagnosis-form"; // reuse same form for create & edit
    }

    // 5. Update existing diagnosis
    @PostMapping("/update/{id}")
    public String updateDiagnosis(@PathVariable Long id,
                                  @ModelAttribute("diagnosis") Diagnosis updatedDiagnosis) {
        diagnosisService.updateDiagnosis(id, updatedDiagnosis);
        return "redirect:/app/diagnoses";
    }

    // 6. Delete
    @GetMapping("/delete/{id}")
    public String deleteDiagnosis(@PathVariable Long id) {
        diagnosisService.deleteDiagnosis(id);
        return "redirect:/app/diagnoses";
    }
}