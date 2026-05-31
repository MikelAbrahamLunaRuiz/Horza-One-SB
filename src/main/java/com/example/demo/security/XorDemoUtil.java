package com.example.demo.security;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * DEMOSTRACIÓN ACADÉMICA - Cifrado XOR con clave.
 *
 * PROPÓSITO EDUCATIVO ÚNICAMENTE:
 *   Esta clase ilustra cómo funciona el cifrado XOR a nivel de bits,
 *   concepto fundamental en criptografía simétrica y en algoritmos
 *   modernos como AES (que usa XOR internamente en su operación AddRoundKey).
 *
 * POR QUÉ XOR SOLO NO ES SEGURO PARA PRODUCCIÓN:
 *   1. Si la clave es más corta que el mensaje, se repite (Vigenère numérico).
 *      → Vulnerable al análisis de frecuencias.
 *   2. Si un atacante obtiene texto cifrado + texto plano, recupera la clave:
 *      clave = texto_cifrado XOR texto_plano (ataques de texto plano conocido).
 *   3. Sin integridad (MAC), el atacante puede modificar bits del cifrado
 *      y predecir el efecto exacto en el texto descifrado.
 *   4. Sin IV/nonce, el mismo texto cifra siempre igual con la misma clave
 *      → permite ataques estadísticos.
 *
 * QUÉ SE USA EN PRODUCCIÓN EN LUGAR DE XOR PURO:
 *   → AES-256-GCM: cifrado simétrico moderno con autenticación integrada.
 *      Disponible en Java con javax.crypto.Cipher.
 *   → El cifrado simétrico real está implementado en EncryptionUtil.java.
 *
 * Este componente solo se usa para propósitos educativos y demostraciones.
 */
@Component
public class XorDemoUtil {

    /**
     * DEMO: Cifra un texto con XOR usando la clave proporcionada.
     * El resultado se codifica en Base64 para ser legible como String.
     *
     * SOLO PARA FINES ACADÉMICOS. No usar para datos reales.
     *
     * @param textoCifrar texto a cifrar
     * @param clave       clave de cifrado (se repetirá si es más corta que el texto)
     * @return texto cifrado en Base64
     */
    public String cifrarXor(String textoCifrar, String clave) {
        byte[] textoBytes = textoCifrar.getBytes(StandardCharsets.UTF_8);
        byte[] claveBytes = clave.getBytes(StandardCharsets.UTF_8);
        byte[] cifrado = aplicarXor(textoBytes, claveBytes);
        return Base64.getEncoder().encodeToString(cifrado);
    }

    /**
     * DEMO: Descifra un texto XOR (la operación XOR es simétrica: aplicar dos veces
     * con la misma clave recupera el original).
     *
     * @param textoCifradoBase64 texto cifrado en Base64
     * @param clave              misma clave usada para cifrar
     * @return texto original
     */
    public String descifrarXor(String textoCifradoBase64, String clave) {
        byte[] cifradoBytes = Base64.getDecoder().decode(textoCifradoBase64);
        byte[] claveBytes = clave.getBytes(StandardCharsets.UTF_8);
        byte[] original = aplicarXor(cifradoBytes, claveBytes);
        return new String(original, StandardCharsets.UTF_8);
    }

    /**
     * Aplica XOR bit a bit entre datos y clave (con repetición de clave).
     * Esta es la operación fundamental: datos[i] XOR clave[i % clave.length].
     *
     * PROPIEDAD MATEMÁTICA CLAVE:
     *   A XOR B = C  →  C XOR B = A
     *   Por eso cifrar y descifrar usan exactamente el mismo código.
     */
    private byte[] aplicarXor(byte[] datos, byte[] clave) {
        if (clave.length == 0) throw new IllegalArgumentException("La clave no puede estar vacía");
        byte[] resultado = new byte[datos.length];
        for (int i = 0; i < datos.length; i++) {
            resultado[i] = (byte) (datos[i] ^ clave[i % clave.length]);
        }
        return resultado;
    }
}
