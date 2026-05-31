package com.example.demo.security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * SEGURIDAD - Cabeceras HTTP de seguridad en todas las respuestas.
 *
 * VULNERABILIDADES MITIGADAS:
 *   CWE-16: Configuración incorrecta de seguridad.
 *   OWASP A05:2021 - Security Misconfiguration.
 *
 * CABECERAS IMPLEMENTADAS:
 *
 *   X-Content-Type-Options: nosniff
 *     - Previene que el navegador "adivine" el tipo MIME de la respuesta.
 *     - Mitiga ataques de tipo MIME sniffing / drive-by download.
 *
 *   X-Frame-Options: DENY
 *     - Prohíbe que la aplicación sea embebida en un <iframe>.
 *     - Mitiga ataques de Clickjacking.
 *
 *   X-XSS-Protection: 1; mode=block
 *     - Activa el filtro XSS nativo del navegador (legacy, complementario a CSP).
 *     - Mitiga Cross-Site Scripting básico en navegadores que lo soporten.
 *
 *   Content-Security-Policy
 *     - Restringe qué recursos puede cargar el navegador.
 *     - Primera defensa real contra XSS.
 *
 *   Referrer-Policy: no-referrer
 *     - Evita que la URL de la API sea enviada como Referrer a terceros.
 *
 *   Cache-Control: no-store
 *     - Impide que respuestas con datos sensibles queden en caché del navegador.
 *
 *   Strict-Transport-Security (HSTS)
 *     - Fuerza HTTPS en futuros accesos durante 1 año.
 *     - Solo efectivo cuando la API sirve sobre HTTPS (Railway, etc.).
 *
 *   Permissions-Policy
 *     - Deshabilita acceso a APIs sensibles del navegador innecesarias para una API REST.
 */
@Component
@Order(1)
public class SecurityHeadersFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (response instanceof HttpServletResponse httpResponse) {
            httpResponse.setHeader("X-Content-Type-Options", "nosniff");
            httpResponse.setHeader("X-Frame-Options", "DENY");
            httpResponse.setHeader("X-XSS-Protection", "1; mode=block");
            httpResponse.setHeader("Content-Security-Policy",
                    "default-src 'none'; frame-ancestors 'none'");
            httpResponse.setHeader("Referrer-Policy", "no-referrer");
            httpResponse.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            httpResponse.setHeader("Pragma", "no-cache");
            httpResponse.setHeader("Strict-Transport-Security",
                    "max-age=31536000; includeSubDomains");
            httpResponse.setHeader("Permissions-Policy",
                    "camera=(), microphone=(), geolocation=(), payment=()");
        }

        chain.doFilter(request, response);
    }
}
