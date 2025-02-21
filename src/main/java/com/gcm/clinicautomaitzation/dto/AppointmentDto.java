package com.gcm.clinicautomaitzation.dto;

import java.util.UUID;

public class AppointmentDto {
    private UUID id;         // Supabase lo genera
    private UUID userId;     // Relación al User
    private String reason;   // Motivo de la cita
    private String status;   // "PENDING", "CONFIRMED", etc.
    private String appointmentTime; // Fecha/hora en formato string (ISO 8601), Supabase la almacena como timestamptz

    public AppointmentDto() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateTime() {
        return appointmentTime;
    }

    public void setDateTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    
}
