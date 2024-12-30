package com.example.spring_boot.service;

import com.example.spring_boot.entity.SickNote;
import com.example.spring_boot.repository.SickNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SickNoteService {

    @Autowired
    private SickNoteRepository sickNoteRepository;

    public SickNote createSickNote(SickNote sickNote) {
        return sickNoteRepository.save(sickNote);
    }

    public List<SickNote> getAllSickNotes() {
        return sickNoteRepository.findAll();
    }

    public SickNote getSickNoteById(Long id) {
        Optional<SickNote> note = sickNoteRepository.findById(id);
        return note.orElse(null);
    }

    public SickNote updateSickNote(Long id, SickNote updated) {
        Optional<SickNote> existingOpt = sickNoteRepository.findById(id);
        if (existingOpt.isPresent()) {
            SickNote existing = existingOpt.get();
            existing.setStartDate(updated.getStartDate());
            existing.setNumberOfDays(updated.getNumberOfDays());
            existing.setEndDate(updated.getEndDate());
            return sickNoteRepository.save(existing);
        }
        return null;
    }

    public boolean deleteSickNote(Long id) {
        if (sickNoteRepository.existsById(id)) {
            sickNoteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
