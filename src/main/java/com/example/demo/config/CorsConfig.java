package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * SEGURIDAD - Configuración centralizada de CORS desde variable de entorno.
 *
 * VULNERABILIDAD MITIGADA:
 *   CWE-942: Configuración permisiva de CORS (Access-Control-Allow-Origin: *).
 *   OWASP A05:2021 - Security Misconfiguration.
 *
 * PROBLEMA ANTERIOR:
 *   Todos los controladores tenían @CrossOrigin(origins = "*"), permitiendo que
 *   CUALQUIER sitio web en internet llame a la API con las credenciales del usuario.
 *   Esto facilita ataques CSRF y accesos no autorizados desde páginas maliciosas.
 *
 * SOLUCIÓN:
 *   Esta configuración centraliza las origenes permitidas.
 *   Se lee desde la variable de entorno CORS_ALLOWED_ORIGINS.
 *
 * CONFIGURACIÓN EN PRODUCCIÓN (Railway):
 *   Variable: CORS_ALLOWED_ORIGINS
 *   Valor:    https://tudominio.com,https://app-movil.tudominio.com
 *
 * CONFIGURACIÓN EN DESARROLLO LOCAL:
 *   Si la variable no existe, usa los valores por defecto (localhost).
 *
 * NOTA: Para que esta configuración tenga efecto completo, los controladores
 *   deberían eliminar las anotaciones @CrossOrigin individuales.
 *   La anotación a nivel de controlador tiene precedencia sobre esta configuración.
 *   En producción, esto se resuelve con Spring Security HttpSecurity.cors().
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${cors.allowed.origins:http://localhost:4200,http://localhost:3000,http://localhost:8080}")
    private String[] allowedOrigins;

    @Value("${cors.max.age:3600}")
    private long maxAge;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders(
                        "Content-Type",
                        "Authorization",
                        "X-Requested-With",
                        "Accept",
                        "Origin"
                )
                .exposedHeaders("X-RateLimit-Remaining", "Retry-After")
                .allowCredentials(false)
                .maxAge(maxAge);
    }
}
