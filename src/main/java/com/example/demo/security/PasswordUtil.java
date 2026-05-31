package com.example.demo.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * SEGURIDAD - Hashing seguro de contraseñas con BCrypt.
 *
 * VULNERABILIDAD MITIGADA:
 *   CWE-256: Almacenamiento de contraseñas en texto plano.
 *   CWE-916: Uso de algoritmos de hash inadecuados.
 *
 * ESTRATEGIA DE MIGRACIÓN TRANSPARENTE:
 *   - Las contraseñas BCrypt comienzan con "$2a$" o "$2b$".
 *   - Las contraseñas antiguas (texto plano) NO comienzan con ese prefijo.
 *   - Al hacer login, si la contraseña almacenada es texto plano y coincide,
 *     se rehashea automáticamente. El usuario no nota nada.
 *   - Esto permite migrar gradualmente sin afectar a ningún usuario.
 *
 * COSTO BCrypt (strength=12):
 *   - ~250ms por hash — suficiente para frustrar ataques de diccionario masivos.
 *   - Cada hash incluye salt aleatorio → mismo password = hash diferente cada vez.
 */
@Component
public class PasswordUtil {

    private static final int BCRYPT_STRENGTH = 12;
    private static final String BCRYPT_PREFIX = "$2";

    private final BCryptPasswordEncoder encoder;

    public PasswordUtil() {
        this.encoder = new BCryptPasswordEncoder(BCRYPT_STRENGTH);
    }

    /** Genera hash BCrypt a partir de una contraseña en texto plano. */
    public String hashear(String contrasenaPlana) {
        if (contrasenaPlana == null || contrasenaPlana.isBlank()) {
            throw new IllegalArgumentException("La contraseña no puede ser vacía");
        }
        return encoder.encode(contrasenaPlana);
    }

    /**
     * Verifica una contraseña contra su hash almacenado.
     * Soporta tanto contraseñas ya hasheadas (BCrypt) como en texto plano (legado).
     *
     * @return true si la contraseña es correcta
     */
    public boolean verificar(String contrasenaPlana, String almacenada) {
        if (contrasenaPlana == null || almacenada == null) return false;
        if (esHashBcrypt(almacenada)) {
            return encoder.matches(contrasenaPlana, almacenada);
        }
        // Compatibilidad con contraseñas legado en texto plano
        return contrasenaPlana.equals(almacenada);
    }

    /**
     * Indica si una contraseña almacenada ya está en formato BCrypt.
     * Permite saber si hay que migrarla en el siguiente login.
     */
    public boolean esHashBcrypt(String almacenada) {
        return almacenada != null && almacenada.startsWith(BCRYPT_PREFIX);
    }

    /**
     * Indica si una contraseña en texto plano necesita ser migrada a BCrypt.
     * Se llama después de verificar que la contraseña es correcta.
     */
    public boolean necesitaMigracion(String almacenada) {
        return !esHashBcrypt(almacenada);
    }
}
