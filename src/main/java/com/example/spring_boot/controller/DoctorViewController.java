package com.example.spring_boot.controller;

import com.example.spring_boot.entity.Doctor;
import com.example.spring_boot.service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/app/doctors")
public class DoctorViewController {

    private final DoctorService doctorService;

    public DoctorViewController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // 1. List all doctors (GET /app/doctors)
    @GetMapping
    public String listDoctors(Model model) {
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "doctor-list";  // thymeleaf template in /templates/doctor-list.html
    }

    // 2. Show form for creating a new doctor (GET /app/doctors/new)
    @GetMapping("/new")
    public String showDoctorCreateForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "doctor-form"; // thymeleaf template in /templates/doctor-form.html
    }

    // 3. Create a new doctor (POST /app/doctors)
    @PostMapping
    public String createDoctor(@ModelAttribute("doctor") Doctor doctor) {
        doctorService.createDoctor(doctor);
        return "redirect:/app/doctors";
    }

    // 4. Show form for editing existing doctor (GET /app/doctors/edit/{id})
    @GetMapping("/edit/{id}")
    public String showDoctorEditForm(@PathVariable("id") Long id, Model model) {
        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor == null) {
            // handle "not found"
            return "redirect:/app/doctors";
        }
        model.addAttribute("doctor", doctor);
        return "doctor-form"; // reuse the same form for create & edit
    }

    // 5. Update an existing doctor (POST /app/doctors/update/{id})
    @PostMapping("/update/{id}")
    public String updateDoctor(@PathVariable("id") Long id, @ModelAttribute("doctor") Doctor updatedDoctor) {
        doctorService.updateDoctor(id, updatedDoctor);
        return "redirect:/app/doctors";
    }

    // 6. Delete a doctor (GET /app/doctors/delete/{id})
    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable("id") Long id) {
        doctorService.deleteDoctor(id);
        return "redirect:/app/doctors";
    }
}
