package com.example.spring_boot.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 255)
    private String password; // Store a hashed password, e.g. BCrypt

    @Column(nullable = false, length = 50)
    private String role; // e.g. "ROLE_ADMIN", "ROLE_DOCTOR", "ROLE_PATIENT"

    // If this user is a patient, link to the patient record (else null)
    @OneToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "patientID")
    private Patient patient;

    // If this user is a doctor, link to the doctor record (else null)
    @OneToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "doctorID")
    private Doctor doctor;

    // Default constructor (required by JPA)
    public User() {
    }

    // Additional constructor for convenience
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    // No setter for 'id' if you want it auto-generated only
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    // Typically you'd store a hashed password, so setPassword might do the hashing.
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}