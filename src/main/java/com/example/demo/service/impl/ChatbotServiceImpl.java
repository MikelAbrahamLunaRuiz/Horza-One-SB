package com.example.demo.service.impl;

import com.example.demo.service.ChatbotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class ChatbotServiceImpl implements ChatbotService {

    private static final Logger log = LoggerFactory.getLogger(ChatbotServiceImpl.class);

    private static final String GROQ_URL = "https://api.groq.com/openai/v1/chat/completions";

    private static final String SYSTEM_PROMPT = """
            Eres el asistente virtual de Horza-One, un sistema de control de acceso y asistencia escolar.

            El sistema gestiona:
            - Registro y control de asistencia de alumnos mediante huella dactilar o tarjeta
            - Gestión de personal, tutores, alumnos y sus roles
            - Grupos y bloques académicos
            - Control de emergencias dentro del plantel
            - Reportes de asistencia exportables a Excel
            - Panel de administración con ajustes de roles y permisos

            Puedes ayudar con:
            - Explicar cómo usar los módulos del sistema (Personal, Alumnos, Grupos, Emergencias, Reportes)
            - Orientar sobre los roles: Administrador, Personal, Tutor
            - Resolver dudas sobre asistencia, acceso y reportes
            - Guiar en la resolución de problemas comunes del sistema

            Responde siempre en español, de forma clara, amigable y concisa (máximo 3 párrafos).
            Si te preguntan algo no relacionado con el sistema escolar, redirige la conversación de forma educada.
            """;

    @Value("${groq.api.key:}")
    private String apiKey;

    @Value("${groq.model:llama-3.3-70b-versatile}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String responder(String pregunta) {
        String key = (apiKey != null) ? apiKey.trim() : "";
        if (key.isBlank()) {
            log.warn("GROQ_API_KEY no configurada — chatbot no disponible");
            return "El asistente no está disponible en este momento. Contacta al administrador del sistema.";
        }

        try {
            Map<String, Object> body = Map.of(
                    "model", model,
                    "messages", List.of(
                            Map.of("role", "system", "content", SYSTEM_PROMPT),
                            Map.of("role", "user", "content", pregunta)
                    ),
                    "max_tokens", 600
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(key);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(GROQ_URL, request, Map.class);

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> choices =
                    (List<Map<String, Object>>) response.getBody().get("choices");

            @SuppressWarnings("unchecked")
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");

            return (String) message.get("content");

        } catch (HttpClientErrorException e) {
            log.error("Groq API error del cliente {} — body: {}", e.getStatusCode(), e.getResponseBodyAsString());
            if (e.getStatusCode().value() == 401) {
                return "API Key inválida o sin permisos. Contacta al administrador.";
            }
            if (e.getStatusCode().value() == 429) {
                return "Demasiadas consultas al asistente. Espera unos minutos e inténtalo de nuevo.";
            }
            return "El asistente no pudo procesar la consulta (" + e.getStatusCode().value() + "). Inténtalo de nuevo.";
        } catch (HttpServerErrorException e) {
            log.error("Groq API error del servidor {} — body: {}", e.getStatusCode(), e.getResponseBodyAsString());
            return "El servicio de IA tuvo un problema temporal. Inténtalo en unos minutos.";
        } catch (Exception e) {
            log.error("Error inesperado al llamar Groq API [{}]: {}", e.getClass().getSimpleName(), e.getMessage());
            return "Ocurrió un error al procesar tu consulta. Por favor, inténtalo de nuevo más tarde.";
        }
    }
}
