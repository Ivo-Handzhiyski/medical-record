package com.example.spring_boot.controller;


import com.example.spring_boot.entity.Patient;
import com.example.spring_boot.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/app/patients")
public class PatientViewController {

    private final PatientService patientService;

    public PatientViewController(PatientService patientService) {
        this.patientService = patientService;
    }

    // 1. List all patients (GET /app/patients)
    @GetMapping
    public String listPatients(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "patient-list";  // thymeleaf template in /templates/patient-list.html
    }

    // 2. Show form for creating a new patient (GET /app/patients/new)
    @GetMapping("/new")
    public String showPatientCreateForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patient-form"; // thymeleaf template in /templates/patient-form.html
    }

    // 3. Create a new patient (POST /app/patients)
    @PostMapping
    public String createPatient(@ModelAttribute("patient") Patient patient) {
        patientService.createPatient(patient);
        return "redirect:/app/patients";
    }

    // 4. Show form for editing existing patient (GET /app/patients/edit/{id})
    @GetMapping("/edit/{id}")
    public String showPatientEditForm(@PathVariable("id") Long id, Model model) {
        Patient patient = patientService.getPatientById(id);
        if (patient == null) {
            // handle "not found"
            return "redirect:/app/patients";
        }
        model.addAttribute("patient", patient);
        return "patient-form"; // reuse the same form for create & edit
    }

    // 5. Update an existing patient (POST /app/patients/update/{id})
    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable("id") Long id, @ModelAttribute("patient") Patient updatedPatient) {
        patientService.updatePatient(id, updatedPatient);
        return "redirect:/app/patients";
    }

    // 6. Delete a patient (GET /app/patients/delete/{id})
    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable("id") Long id) {
        patientService.deletePatient(id);
        return "redirect:/app/patients";
    }
}