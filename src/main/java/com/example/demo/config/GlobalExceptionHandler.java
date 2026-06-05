package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * SEGURIDAD - Manejador global de errores que previene fuga de información interna.
 *
 * VULNERABILIDADES MITIGADAS:
 *   CWE-209: Generación de mensaje de error que contiene información sensible.
 *   CWE-550: Información del servidor expuesta a través de páginas de error.
 *   OWASP A05:2021 - Security Misconfiguration.
 *
 * PROBLEMA ANTERIOR:
 *   Spring Boot por defecto devuelve stack traces completos en respuestas de error,
 *   revelando:
 *   - Versión exacta de Spring Boot y librerías.
 *   - Rutas internas del servidor (nombres de paquetes, clases, métodos).
 *   - Detalles del esquema de base de datos en errores JPA.
 *   - Mensajes de excepción que confirman si un usuario/recurso existe.
 *
 * SOLUCIÓN:
 *   - Los errores se loguean COMPLETOS en el servidor (para diagnóstico interno).
 *   - Al cliente se devuelve solo un mensaje genérico + código HTTP apropiado.
 *   - Se incluye timestamp para correlación con logs del servidor.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /** Errores de validación de entrada (@Valid, @NotBlank, @Size, etc.) */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarValidacion(
            MethodArgumentNotValidException ex) {

        List<String> errores = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .toList();

        return respuesta(HttpStatus.BAD_REQUEST, "Datos de entrada inválidos", errores);
    }

    /** Método HTTP no soportado (p.ej. GET en un endpoint que solo acepta POST) */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> manejarMetodoNoSoportado(
            HttpRequestMethodNotSupportedException ex) {
        return respuesta(HttpStatus.METHOD_NOT_ALLOWED,
                "Método HTTP no permitido: " + ex.getMethod(), null);
    }

    /** Ruta no encontrada (recurso estático o endpoint inexistente) */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, Object>> manejarRecursoNoEncontrado(
            NoResourceFoundException ex) {
        log.warn("Recurso no encontrado: {}", ex.getMessage());
        return respuesta(HttpStatus.NOT_FOUND, "Recurso no encontrado", null);
    }

    /** Parámetros de tipo incorrecto en la URL (p.ej. /api/usuarios/abc en lugar de número) */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> manejarTipoIncorrecto(
            MethodArgumentTypeMismatchException ex) {

        log.warn("Parámetro de tipo incorrecto: {}", ex.getName());
        return respuesta(HttpStatus.BAD_REQUEST, "Parámetro inválido en la URL", null);
    }

    /**
     * Excepciones de negocio (RuntimeException).
     * Se loguea el mensaje real pero al cliente se envía un mensaje genérico
     * para no revelar detalles internos (p.ej. "Usuario no encontrado con matrícula: 999").
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> manejarRuntime(RuntimeException ex) {
        log.error("Error de negocio: {}", ex.getMessage());
        return respuesta(HttpStatus.BAD_REQUEST, "La operación no pudo completarse", null);
    }

    /**
     * Cualquier otro error inesperado.
     * El stack trace completo queda en logs del servidor, nunca llega al cliente.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> manejarGeneral(Exception ex) {
        log.error("Error interno inesperado", ex);
        return respuesta(HttpStatus.INTERNAL_SERVER_ERROR,
                "Error interno del servidor. Contacta al administrador.", null);
    }

    // --- privado ---

    private ResponseEntity<Map<String, Object>> respuesta(
            HttpStatus status, String mensaje, List<String> detalles) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());
        body.put("error", mensaje);
        if (detalles != null && !detalles.isEmpty()) {
            body.put("detalles", detalles);
        }
        return ResponseEntity.status(status).body(body);
    }
}
