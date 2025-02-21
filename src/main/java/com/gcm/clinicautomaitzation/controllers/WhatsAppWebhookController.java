package com.gcm.clinicautomaitzation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gcm.clinicautomaitzation.services.WhatsappService;

import java.util.Map;

@RestController
@RequestMapping("/webhook")
public class WhatsAppWebhookController {

    @Autowired
    private WhatsappService whatsAppService;

    // Endpoint para la verificación inicial del webhook con Meta
    @GetMapping
    public ResponseEntity<String> verifyWebhook(
            @RequestParam("hub.mode") String mode,
            @RequestParam("hub.challenge") String challenge,
            @RequestParam("hub.verify_token") String verifyToken) {

        // Cambia "tu-token-secreto" por el token configurado en Meta
        if ("subscribe".equals(mode) && "tu-token-secreto".equals(verifyToken)) {
            return ResponseEntity.ok(challenge);
        } else {
            return ResponseEntity.status(403).body("Verificación fallida");
        }
    }

    // Endpoint para recibir mensajes
    @PostMapping
    public ResponseEntity<Void> receiveMessage(@RequestBody Map<String, Object> payload) {
        System.out.println("📩 [WhatsAppWebhook] Mensaje recibido: " + payload);
        whatsAppService.processIncomingMessage(payload);
        return ResponseEntity.ok().build();
    }
}
