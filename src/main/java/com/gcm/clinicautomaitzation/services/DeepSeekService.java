package com.gcm.clinicautomaitzation.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeepSeekService {

    @Value("${deepseek.api.url}")
    private String apiUrl;

    @Value("${deepseek.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getDeepSeekResponse(String userMessage) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "deepseek-chat");
        requestBody.put("temperature", 0.3); // Respuestas más coherentes
        requestBody.put("max_tokens", 300);  // Respuestas más detalladas
        requestBody.put("messages", List.of(
                Map.of("role", "system", "content", "Eres un asistente virtual inteligente para la Clínica SaludVida. La clínica SaludVida se especializa en medicina general, pediatría, dermatología y fisioterapia. Estamos ubicados en Calle Mayor 123, Madrid, España. Nuestro horario de atención es de lunes a viernes de 9:00 a 19:00. Responde siempre en español, de forma clara y profesional.\n\nInformación adicional:\n📞 Teléfono de contacto: +34 912 345 678\n📧 Correo electrónico: contacto@saludvida.com\n💶 Aceptamos seguros médicos: Sanitas, Adeslas y Mapfre. También disponemos de tarifas privadas con precios competitivos.\n🏅 Nuestro equipo médico está formado por especialistas altamente cualificados: Dr. Juan Pérez (medicina general), Dra. Laura Martínez (dermatología), Dr. Carlos Gómez (pediatría) y María López (fisioterapeuta).\n📋 Para exámenes médicos como análisis de sangre, se recomienda ayuno de 8 horas. Para revisiones dermatológicas, traer historial médico si está disponible.\n🏖 Política de cancelación: Las citas pueden cancelarse o reprogramarse con al menos 24 horas de antelación.\n\n⚠️ Muy importante: Solo debes responder preguntas relacionadas con la clínica, sus servicios, especialidades, equipo médico, precios, seguros aceptados, horarios, ubicación o temas directamente relacionados con reservas, consultas o cancelaciones de citas. Si el mensaje del usuario no está relacionado con estos temas, responde educadamente indicando: \"Lo siento, solo puedo responder preguntas relacionadas con la Clínica SaludVida, sus servicios o la gestión de citas.\" No generes respuestas a temas no relacionados para evitar el uso innecesario de recursos.\n\nTu tarea es responder de forma breve pero informativa, proporcionando orientación clara y profesional."),
                Map.of("role", "user", "content", userMessage)
        ));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, request, Map.class);
        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");

        return (String) ((Map<String, Object>) choices.get(0).get("message")).get("content");
    }
}
