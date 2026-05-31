package com.example.demo.controller;

import com.example.demo.dto.ChatRequest;
import com.example.demo.dto.ChatResponse;
import com.example.demo.security.RateLimiterService;
import com.example.demo.service.ChatbotService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ia")
@CrossOrigin(origins = "*")
public class ChatbotController {

    @Autowired
    private ChatbotService chatbotService;

    @Autowired
    private RateLimiterService rateLimiterService;

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(
            @Valid @RequestBody ChatRequest request,
            HttpServletRequest httpRequest) {

        String ip = obtenerIp(httpRequest);
        String claveChat = "CHAT_" + ip;

        if (rateLimiterService.estaBloqueada(claveChat)) {
            long restante = rateLimiterService.segundosRestantesBloqueo(claveChat);
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .header("Retry-After", String.valueOf(restante))
                    .body(new ChatResponse(
                            "Demasiadas consultas seguidas. Espera unos minutos antes de continuar.",
                            false));
        }

        rateLimiterService.registrarIntentiFallido(claveChat);

        String respuesta = chatbotService.responder(request.getPregunta());

        return ResponseEntity.ok(new ChatResponse(respuesta, true));
    }

    private String obtenerIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isBlank()) {
            return forwarded.split(",")[0].trim();
        }
        String realIp = request.getHeader("X-Real-IP");
        if (realIp != null && !realIp.isBlank()) {
            return realIp;
        }
        return request.getRemoteAddr();
    }
}
