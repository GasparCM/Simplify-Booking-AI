package com.gcm.clinicautomaitzation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.gcm.clinicautomaitzation.dto.AppointmentDto;

import java.util.*;

@Service
public class WhatsappService {

    @Value("${whatsapp.token}")
    private String whatsappToken;

    @Value("${whatsapp.phoneNumberId}")
    private String phoneNumberId;

    @Autowired
    private DeepSeekService deepSeekService;

    @Autowired
    private AppointmentService appointmentService;

    private final RestTemplate restTemplate = new RestTemplate();

    // Procesar mensajes entrantes con análisis de intención
    public void processIncomingMessage(Map<String, Object> payload) {
        try {
            List<Map<String, Object>> entry = (List<Map<String, Object>>) payload.get("entry");
            Map<String, Object> value = (Map<String, Object>) ((List<?>) entry.get(0).get("changes")).get(0);
            Map<String, Object> message = (Map<String, Object>) ((List<?>) value.get("messages")).get(0);

            String from = (String) message.get("from");
            String text = (String) ((Map<String, Object>) message.get("text")).get("body");

            System.out.println("📨 Mensaje recibido de: " + from + " - Texto: " + text);

            String aiResponse = deepSeekService.getDeepSeekResponse(text);
            System.out.println("🧠 Respuesta de DeepSeek: " + aiResponse);

            handleAIResponse(from, text, aiResponse);
        } catch (Exception e) {
            System.out.println("❌ [WhatsAppService] Error al procesar mensaje: " + e.getMessage());
        }
    }

    // Analiza la respuesta de DeepSeek y ejecuta la acción adecuada
    private void handleAIResponse(String phone, String userMessage, String aiResponse) {
        if (aiResponse.toLowerCase().contains("reservar")) {
            handleReservationRequest(phone, userMessage);
        } else if (aiResponse.toLowerCase().contains("consultar")) {
            handleExistingAppointments(phone);
        } else if (aiResponse.toLowerCase().contains("cancelar")) {
            handleCancelAppointment(phone);
        } else {
            sendWhatsAppMessage(phone, aiResponse);
        }
    }

    private void handleReservationRequest(String phone, String message) {
        Date fechaCita = new Date(); // Podríamos mejorar reconociendo fechas concretas con IA
        String motivo = "Consulta general";

        appointmentService.registerUserIfNotExists(phone, "Cliente WhatsApp");
        appointmentService.createAppointment(appointmentService.registerUserIfNotExists(phone, "Cliente WhatsApp"), fechaCita, motivo);

        sendWhatsAppMessage(phone, "✅ ¡Cita reservada para hoy! 📅 Si deseas cambiar la fecha, indícala.");
    }

    private void handleExistingAppointments(String phone) {
        var user = appointmentService.registerUserIfNotExists(phone, "Cliente WhatsApp");
        List<AppointmentDto> citas = appointmentService.getAppointments(user); // o user.getId() si aplicas la Opción B
        if (citas.isEmpty()) {
            sendWhatsAppMessage(phone, "📭 No tienes citas registradas actualmente.");
        } else {
            StringBuilder response = new StringBuilder("📋 Tus citas:\n");
            for (var cita : citas) {
                response.append("- ").append(cita.getReason())
                        .append(" en ").append(cita.getDateTime()).append("\n");
            }
            sendWhatsAppMessage(phone, response.toString());
        }
    }

    private void handleCancelAppointment(String phone) {
        sendWhatsAppMessage(phone, "🚫 Por ahora, la opción de cancelar citas no está disponible. ¡Pronto la activaremos!");
    }

    public void sendWhatsAppMessage(String to, String message) {
        String endpoint = "https://graph.facebook.com/v17.0/" + phoneNumberId + "/messages";

        Map<String, Object> body = new HashMap<>();
        body.put("messaging_product", "whatsapp");
        body.put("to", to);
        body.put("type", "text");
        body.put("text", Map.of("body", message));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(whatsappToken);

        restTemplate.postForEntity(endpoint, new HttpEntity<>(body, headers), String.class);
        System.out.println("📤 Mensaje enviado a " + to + ": " + message);
    }
}