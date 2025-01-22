package com.example.spring_boot.controller;

import com.example.spring_boot.entity.Treatment;
import com.example.spring_boot.service.TreatmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/app/treatments")
public class TreatmentViewController {

    private final TreatmentService treatmentService;

    public TreatmentViewController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    // 1. List all treatments
    @GetMapping
    public String listTreatments(Model model) {
        model.addAttribute("treatments", treatmentService.getAllTreatments());
        return "treatment-list"; // e.g. /templates/treatment-list.html
    }

    // 2. Show form to create new treatment
    @GetMapping("/new")
    public String showTreatmentCreateForm(Model model) {
        model.addAttribute("treatment", new Treatment());
        return "treatment-form"; // e.g. /templates/treatment-form.html
    }

    // 3. Create new treatment
    @PostMapping
    public String createTreatment(@ModelAttribute("treatment") Treatment treatment) {
        treatmentService.createTreatment(treatment);
        return "redirect:/app/treatments";
    }

    // 4. Show form to edit existing treatment
    @GetMapping("/edit/{id}")
    public String showTreatmentEditForm(@PathVariable Long id, Model model) {
        Treatment existing = treatmentService.getTreatmentById(id);
        if (existing == null) {
            return "redirect:/app/treatments";
        }
        model.addAttribute("treatment", existing);
        return "treatment-form";
    }

    // 5. Update existing treatment
    @PostMapping("/update/{id}")
    public String updateTreatment(@PathVariable Long id,
                                  @ModelAttribute("treatment") Treatment updatedTreatment) {
        treatmentService.updateTreatment(id, updatedTreatment);
        return "redirect:/app/treatments";
    }

    // 6. Delete
    @GetMapping("/delete/{id}")
    public String deleteTreatment(@PathVariable Long id) {
        treatmentService.deleteTreatment(id);
        return "redirect:/app/treatments";
    }
}