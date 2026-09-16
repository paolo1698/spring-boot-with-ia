package com.example.springBootIA.persistence.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "appointments")
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID patientId;
    private LocalDateTime dateTime;
    private String reason;

    // Constructor vacío requerido por JPA
    public AppointmentEntity() {}

    // Constructor sin el ID para creaciones nuevas
    public AppointmentEntity(UUID patientId, LocalDateTime dateTime, String reason) {
        this.patientId = patientId;
        this.dateTime = dateTime;
        this.reason = reason;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getReason() {
        return reason;
    }
}