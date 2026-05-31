package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.security.RateLimiterService;
import com.example.demo.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * SEGURIDAD - Controlador de autenticación con Rate Limiting.
 *
 * MEJORAS APLICADAS:
 *
 * 1. RATE LIMITING (CWE-307, OWASP A07):
 *    - Máximo 5 intentos fallidos por IP en 15 minutos.
 *    - Respuesta 429 Too Many Requests con cabecera Retry-After.
 *    - El contador se resetea automáticamente en login exitoso.
 *
 * 2. EXTRACCIÓN SEGURA DE IP (CWE-348):
 *    - Soporta cabeceras de proxies inversos (X-Forwarded-For, X-Real-IP).
 *    - Útil cuando la API está detrás de Railway/Nginx/CloudFlare.
 *
 * 3. LOGGING SIN CREDENCIALES (CWE-532):
 *    - Los logs registran IP y resultado pero NUNCA la contraseña recibida.
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private RateLimiterService rateLimiterService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest loginRequest,
            HttpServletRequest httpRequest) {

        String ip = extraerIp(httpRequest);

        // Verificar si la IP está bloqueada por exceso de intentos
        if (rateLimiterService.estaBloqueada(ip)) {
            long segundos = rateLimiterService.segundosRestantesBloqueo(ip);
            log.warn("IP bloqueada por rate limiting: {} — {}s restantes", ip, segundos);

            LoginResponse bloqueado = new LoginResponse();
            bloqueado.setSuccess(false);
            bloqueado.setMensaje("Demasiados intentos fallidos. Intenta de nuevo en "
                    + (segundos / 60 + 1) + " minuto(s).");

            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .header(HttpHeaders.RETRY_AFTER, String.valueOf(segundos))
                    .body(bloqueado);
        }

        LoginResponse response = loginService.login(loginRequest);

        if (response.getSuccess()) {
            // Login exitoso: resetear contador de intentos
            rateLimiterService.resetear(ip);
            log.info("Login exitoso desde IP: {}", ip);
            return ResponseEntity.ok(response);
        } else {
            // Login fallido: incrementar contador
            rateLimiterService.registrarIntentiFallido(ip);
            int intentos = rateLimiterService.intentosFallidos(ip);
            log.warn("Login fallido desde IP: {} — intento {}/5", ip, intentos);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    /**
     * Extrae la IP real del cliente soportando proxies inversos.
     * X-Forwarded-For puede contener múltiples IPs separadas por coma;
     * la primera es siempre la del cliente original.
     */
    private String extraerIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isBlank()) {
            return xForwardedFor.split(",")[0].trim();
        }
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isBlank()) {
            return xRealIp.trim();
        }
        return request.getRemoteAddr();
    }
}
