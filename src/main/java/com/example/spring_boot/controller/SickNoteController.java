package com.example.spring_boot.controller;

import com.example.spring_boot.entity.SickNote;
import com.example.spring_boot.service.SickNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sicknotes")
public class SickNoteController {

    @Autowired
    private SickNoteService sickNoteService;

    @PostMapping
    public ResponseEntity<SickNote> createSickNote(@RequestBody SickNote sickNote) {
        SickNote saved = sickNoteService.createSickNote(sickNote);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<SickNote>> getAllSickNotes() {
        List<SickNote> notes = sickNoteService.getAllSickNotes();
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SickNote> getSickNoteById(@PathVariable Long id) {
        SickNote note = sickNoteService.getSickNoteById(id);
        if (note == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(note);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SickNote> updateSickNote(@PathVariable Long id, @RequestBody SickNote updated) {
        SickNote note = sickNoteService.updateSickNote(id, updated);
        if (note == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(note);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSickNote(@PathVariable Long id) {
        boolean deleted = sickNoteService.deleteSickNote(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
