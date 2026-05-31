package com.example.demo.security;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

/**
 * SEGURIDAD - Verificación de integridad de datos con SHA-256 y SHA-512.
 *
 * VULNERABILIDADES MITIGADAS:
 *   CWE-345: Verificación insuficiente de integridad de datos.
 *   CWE-328: Uso de algoritmos hash débiles.
 *
 * CASOS DE USO EN ESTE SISTEMA:
 *   1. Verificar integridad de archivos subidos (PDFs/fotos) antes de procesarlos.
 *   2. Generar huellas digitales de expedientes para detectar manipulaciones.
 *   3. Crear tokens de verificación para operaciones críticas (baja de usuarios).
 *   4. Generar identificadores únicos reproducibles a partir de datos.
 *
 * DIFERENCIA CON BCrypt (en PasswordUtil):
 *   - SHA-256/512 son funciones de hash DETERMINISTAS (mismo input = mismo hash).
 *     Útiles para integridad de archivos y verificación de datos.
 *   - BCrypt incluye salt aleatorio → NO determinista.
 *     Obligatorio para contraseñas (evita tablas rainbow).
 *   - NUNCA usar SHA-256 para contraseñas de usuarios sin salt adicional.
 */
@Component
public class DataIntegrityUtil {

    /**
     * Genera un hash SHA-256 del texto dado.
     * Retorna la representación hexadecimal (64 caracteres).
     *
     * Ejemplo de uso: verificar que un PDF no fue alterado después de subirse.
     */
    public String sha256Hex(String datos) {
        return calcularHash("SHA-256", datos);
    }

    /**
     * Genera un hash SHA-512 del texto dado.
     * Retorna la representación hexadecimal (128 caracteres).
     * Mayor resistencia a colisiones que SHA-256.
     */
    public String sha512Hex(String datos) {
        return calcularHash("SHA-512", datos);
    }

    /**
     * Genera un hash SHA-256 de un arreglo de bytes (para archivos binarios).
     * Útil para verificar integridad de archivos PDF o imágenes subidas.
     */
    public String sha256Bytes(byte[] datos) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(datos);
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 no disponible en JVM", e);
        }
    }

    /**
     * Verifica que el hash SHA-256 de los datos coincide con el hash esperado.
     * Usa comparación de tiempo constante para evitar timing attacks.
     *
     * @param datos  datos a verificar
     * @param hashEsperado hash hexadecimal esperado
     * @return true si los datos no han sido alterados
     */
    public boolean verificarIntegridad(String datos, String hashEsperado) {
        if (datos == null || hashEsperado == null) return false;
        String hashCalculado = sha256Hex(datos);
        return MessageDigest.isEqual(
                hashCalculado.getBytes(StandardCharsets.UTF_8),
                hashEsperado.getBytes(StandardCharsets.UTF_8)
        );
    }

    /**
     * Genera una huella digital de un expediente para detectar manipulaciones.
     * Combina campos clave en un único hash reproducible.
     *
     * @param matricula matrícula del usuario
     * @param nombre    nombre completo
     * @param rfc       RFC del usuario
     * @return hash SHA-256 que actúa como firma del expediente
     */
    public String huellaExpediente(Integer matricula, String nombre, String rfc) {
        String datos = matricula + "|" + nombre + "|" + rfc;
        return sha256Hex(datos);
    }

    // --- privado ---

    private String calcularHash(String algoritmo, String datos) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algoritmo);
            byte[] hash = digest.digest(datos.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(algoritmo + " no disponible en JVM", e);
        }
    }
}
