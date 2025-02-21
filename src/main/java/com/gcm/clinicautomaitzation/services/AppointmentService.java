package com.gcm.clinicautomaitzation.services;

import com.gcm.clinicautomaitzation.dto.UserDto;
import com.gcm.clinicautomaitzation.dto.AppointmentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

@Service
public class AppointmentService {

    @Autowired
    private SupabaseApiService supabaseApiService;

    /**
     * Busca un usuario por teléfono; si no existe, lo crea con el nombre dado.
     */
    public UserDto registerUserIfNotExists(String phone, String name) {
        UserDto existing = supabaseApiService.getUserByPhone(phone);
        if (existing == null) {
            // No existe => crearlo
            return supabaseApiService.createUser(phone, name);
        } else {
            // Ya existe => devolverlo
            return existing;
        }
    }

    /**
     * Crea una cita PENDING para un usuario, en una fecha dada.
     * dateTime se envía como ISO 8601 string a Supabase (ej: "2025-03-01T10:00:00Z").
     */
    public AppointmentDto createAppointment(UserDto user, Date date, String reason) {
        // Formateamos la fecha a ISO 8601 (ejemplo simplificado):
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String dateTimeStr = isoFormat.format(date);

        return supabaseApiService.createAppointment(
            user.getId(),
            reason,
            dateTimeStr
        );
    }

    /**
     * Obtiene la lista de citas de un usuario.
     */
    public List<AppointmentDto> getAppointments(UserDto user) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("El usuario es nulo o no tiene ID");
        }
        return supabaseApiService.getAppointmentsByUser(user.getId());
    }
}
