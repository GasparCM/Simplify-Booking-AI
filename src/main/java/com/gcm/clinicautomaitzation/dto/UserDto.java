package com.gcm.clinicautomaitzation.dto;

import java.util.UUID;

public class UserDto {
    private UUID id;      // El id que retorna Supabase
    private String phone; // Teléfono
    private String name;  // Nombre

    public UserDto() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
