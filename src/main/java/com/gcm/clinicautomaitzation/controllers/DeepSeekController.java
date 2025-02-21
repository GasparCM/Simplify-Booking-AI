package com.gcm.clinicautomaitzation.controllers;

import com.gcm.clinicautomaitzation.services.DeepSeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deepseek")
public class DeepSeekController {

    @Autowired
    private DeepSeekService deepSeekService;

    @PostMapping("/test")
    public ResponseEntity<String> testDeepSeek(@RequestBody String mensaje) {
        String respuesta = deepSeekService.getDeepSeekResponse(mensaje);
        return ResponseEntity.ok(respuesta);
    }
}
