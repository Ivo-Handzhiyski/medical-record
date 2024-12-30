package com.example.spring_boot.entity;


import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "sick_note")
public class SickNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sickNoteID;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private int numberOfDays;

    // endDate can be calculated at the application level or with a DB trigger
    private LocalDate endDate;

    // Constructors
    public SickNote() {}

    public SickNote(LocalDate startDate, int numberOfDays, LocalDate endDate) {
        this.startDate = startDate;
        this.numberOfDays = numberOfDays;
        this.endDate = endDate;
    }

    // Getters and Setters
    public Long getSickNoteID() {
        return sickNoteID;
    }

    public void setSickNoteID(Long sickNoteID) {
        this.sickNoteID = sickNoteID;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}