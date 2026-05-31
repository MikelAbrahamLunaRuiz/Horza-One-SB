package com.example.demo.security;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * SEGURIDAD - Rate limiting en memoria para protección contra fuerza bruta.
 *
 * VULNERABILIDADES MITIGADAS:
 *   CWE-307: Restricción inadecuada de intentos de autenticación excesivos.
 *   CWE-799: Control inadecuado de frecuencia de interacción.
 *   OWASP A07:2021 - Fallas de identificación y autenticación.
 *
 * FUNCIONAMIENTO:
 *   - Rastrea intentos fallidos por dirección IP.
 *   - Después de MAX_INTENTOS fallidos, bloquea esa IP por VENTANA_BLOQUEO segundos.
 *   - Los intentos exitosos resetean el contador de esa IP.
 *   - ConcurrentHashMap garantiza seguridad en entornos multi-hilo.
 *
 * LIMITACIÓN CONOCIDA:
 *   - El estado se pierde al reiniciar el servidor (in-memory).
 *   - Para producción distribuida, usar Redis con spring-data-redis.
 */
@Service
public class RateLimiterService {

    /** Máximo de intentos fallidos antes de bloquear */
    private static final int MAX_INTENTOS = 5;

    /** Segundos que permanece bloqueada la IP (15 minutos) */
    private static final long VENTANA_BLOQUEO_SEGUNDOS = 900L;

    private record EntradaBloqueo(AtomicInteger intentos, Instant bloqueadoHasta) {
        EntradaBloqueo(int intentosIniciales) {
            this(new AtomicInteger(intentosIniciales), null);
        }
    }

    private final ConcurrentHashMap<String, EntradaBloqueo> registro = new ConcurrentHashMap<>();

    /**
     * Verifica si una IP está bloqueada actualmente.
     *
     * @param ip dirección IP del cliente
     * @return true si la IP está bloqueada y debe rechazarse la petición
     */
    public boolean estaBloqueada(String ip) {
        EntradaBloqueo entrada = registro.get(ip);
        if (entrada == null) return false;
        if (entrada.bloqueadoHasta() != null && Instant.now().isBefore(entrada.bloqueadoHasta())) {
            return true;
        }
        // Si el bloqueo expiró, limpiar
        if (entrada.bloqueadoHasta() != null) {
            registro.remove(ip);
        }
        return false;
    }

    /**
     * Registra un intento fallido de login para la IP indicada.
     * Si supera MAX_INTENTOS, la bloquea automáticamente.
     *
     * @param ip dirección IP del cliente
     */
    public void registrarIntentiFallido(String ip) {
        registro.compute(ip, (key, entrada) -> {
            if (entrada == null) {
                return new EntradaBloqueo(new AtomicInteger(1), null);
            }
            int nuevoValor = entrada.intentos().incrementAndGet();
            if (nuevoValor >= MAX_INTENTOS) {
                Instant bloqueadoHasta = Instant.now().plusSeconds(VENTANA_BLOQUEO_SEGUNDOS);
                return new EntradaBloqueo(entrada.intentos(), bloqueadoHasta);
            }
            return new EntradaBloqueo(entrada.intentos(), null);
        });
    }

    /**
     * Resetea el contador de intentos fallidos para la IP (login exitoso).
     *
     * @param ip dirección IP del cliente
     */
    public void resetear(String ip) {
        registro.remove(ip);
    }

    /**
     * Retorna cuántos segundos faltan para que expire el bloqueo.
     * Retorna 0 si no está bloqueada.
     */
    public long segundosRestantesBloqueo(String ip) {
        EntradaBloqueo entrada = registro.get(ip);
        if (entrada == null || entrada.bloqueadoHasta() == null) return 0L;
        long restante = entrada.bloqueadoHasta().getEpochSecond() - Instant.now().getEpochSecond();
        return Math.max(0L, restante);
    }

    /**
     * Retorna los intentos fallidos actuales de la IP.
     */
    public int intentosFallidos(String ip) {
        EntradaBloqueo entrada = registro.get(ip);
        return entrada == null ? 0 : entrada.intentos().get();
    }
}
