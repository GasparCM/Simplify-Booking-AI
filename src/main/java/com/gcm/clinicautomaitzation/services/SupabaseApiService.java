package com.gcm.clinicautomaitzation.services;

import com.gcm.clinicautomaitzation.dto.UserDto;
import com.gcm.clinicautomaitzation.dto.AppointmentDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class SupabaseApiService {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.key}")
    private String supabaseKey;

    private final RestTemplate restTemplate = new RestTemplate();

    // 1. Crea un usuario en la tabla "users"
    public UserDto createUser(String phone, String name) {
        String endpoint = supabaseUrl + "/users";

        Map<String, Object> body = new HashMap<>();
        body.put("phone", phone);
        body.put("name", name);

        HttpHeaders headers = buildHeaders();
        // "Prefer: return=representation" => Supabase devuelve el registro creado
        headers.set("Prefer", "return=representation");

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<UserDto[]> response = restTemplate.postForEntity(endpoint, request, UserDto[].class);
        UserDto[] users = response.getBody();
        if (users != null && users.length > 0) {
            return users[0]; 
        }
        return null;
    }

    // 2. Buscar usuario por teléfono => GET /users?phone=eq.<phone>
    public UserDto getUserByPhone(String phone) {
        // Se recomienda URL-encodear el phone, pero para el ejemplo simple:
        String endpoint = supabaseUrl + "/users?phone=eq." + phone;

        HttpHeaders headers = buildHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<UserDto[]> response = restTemplate.exchange(
                endpoint, HttpMethod.GET, request, UserDto[].class);

        UserDto[] users = response.getBody();
        if (users != null && users.length > 0) {
            return users[0];
        }
        return null;
    }

    // 3. Crear una cita en la tabla "appointments"
    public AppointmentDto createAppointment(UUID userId, String reason, String appointmentTime) {
        String endpoint = supabaseUrl + "/appointments";
    
        Map<String, Object> body = new HashMap<>();
        body.put("user_id", userId);
        body.put("reason", reason);
        body.put("status", "PENDING");
        // Nota: la columna en tu tabla es appointment_time
        body.put("appointment_time", appointmentTime);
    
        HttpHeaders headers = buildHeaders();
        headers.set("Prefer", "return=representation");
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
    
        ResponseEntity<AppointmentDto[]> response =
            restTemplate.postForEntity(endpoint, request, AppointmentDto[].class);
    
        AppointmentDto[] created = response.getBody();
        if (created != null && created.length > 0) {
            return created[0];
        }
        return null;
    }
    

    // 4. Obtener citas de un usuario => GET /appointments?userId=eq.<id>
    public List<AppointmentDto> getAppointmentsByUser(UUID userId) {
        String endpoint = supabaseUrl + "/appointments?userId=eq." + userId;

        HttpHeaders headers = buildHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<AppointmentDto[]> response = restTemplate.exchange(
                endpoint, HttpMethod.GET, request, AppointmentDto[].class);

        AppointmentDto[] appointments = response.getBody();
        if (appointments != null) {
            return Arrays.asList(appointments);
        }
        return Collections.emptyList();
    }

    // Construye los headers (API key, auth, JSON, etc.)
    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // API Key
        headers.set("apikey", supabaseKey);
        headers.set("Authorization", "Bearer " + supabaseKey);
        return headers;
    }
}
