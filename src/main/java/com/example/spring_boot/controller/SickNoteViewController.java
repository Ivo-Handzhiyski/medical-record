package com.example.spring_boot.controller;


import com.example.spring_boot.entity.SickNote;
import com.example.spring_boot.service.SickNoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/app/sicknotes")
public class SickNoteViewController {

    private final SickNoteService sickNoteService;

    public SickNoteViewController(SickNoteService sickNoteService) {
        this.sickNoteService = sickNoteService;
    }

    // 1. List all sick notes
    @GetMapping
    public String listSickNotes(Model model) {
        model.addAttribute("sickNotes", sickNoteService.getAllSickNotes());
        return "sicknote-list"; // e.g. /templates/sicknote-list.html
    }

    // 2. Show form to create new sick note
    @GetMapping("/new")
    public String showSickNoteCreateForm(Model model) {
        model.addAttribute("sickNote", new SickNote());
        return "sicknote-form"; // e.g. /templates/sicknote-form.html
    }

    // 3. Create new sick note
    @PostMapping
    public String createSickNote(@ModelAttribute("sickNote") SickNote sickNote) {
        sickNoteService.createSickNote(sickNote);
        return "redirect:/app/sicknotes";
    }

    // 4. Show form to edit existing sick note
    @GetMapping("/edit/{id}")
    public String showSickNoteEditForm(@PathVariable Long id, Model model) {
        SickNote existing = sickNoteService.getSickNoteById(id);
        if (existing == null) {
            return "redirect:/app/sicknotes";
        }
        model.addAttribute("sickNote", existing);
        return "sicknote-form";
    }

    // 5. Update existing sick note
    @PostMapping("/update/{id}")
    public String updateSickNote(@PathVariable Long id,
                                 @ModelAttribute("sickNote") SickNote updated) {
        sickNoteService.updateSickNote(id, updated);
        return "redirect:/app/sicknotes";
    }

    // 6. Delete
    @GetMapping("/delete/{id}")
    public String deleteSickNote(@PathVariable Long id) {
        sickNoteService.deleteSickNote(id);
        return "redirect:/app/sicknotes";
    }
}