package com.gcm.clinicautomaitzation.controllers;

import com.gcm.clinicautomaitzation.dto.UserDto;
import com.gcm.clinicautomaitzation.dto.AppointmentDto;
import com.gcm.clinicautomaitzation.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private AppointmentService appointmentService;

    // 1. Prueba crear un usuario con datos fijos
    // POST /test/createUser?phone=34666000000&name=Pepe
    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(
            @RequestParam String phone,
            @RequestParam String name) {

        // Registra si no existe, retorna el UserDto
        UserDto user = appointmentService.registerUserIfNotExists(phone, name);
        return ResponseEntity.ok(user);
    }

    // 2. Prueba crear una cita para un usuario existente
    // POST /test/createAppointment?phone=34666000000&reason=DolorCabeza
    @PostMapping("/createAppointment")
    public ResponseEntity<AppointmentDto> createAppointment(
            @RequestParam String phone,
            @RequestParam String reason) {

        // A) Busca el user por phone
        UserDto user = appointmentService.registerUserIfNotExists(phone, "Temporal");
        // B) Crea la cita para "ahora"
        // Formatearemos la fecha en AppointmentService
        Date now = new Date();
        AppointmentDto appt = appointmentService.createAppointment(user, now, reason);
        return ResponseEntity.ok(appt);
    }
}
