package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.model.Tutor;
import com.example.demo.model.Usuario;
import com.example.demo.respository.PermisoPersonalizadoRepository;
import com.example.demo.respository.TutorRepository;
import com.example.demo.respository.UsuarioRepository;
import com.example.demo.security.PasswordUtil;
import com.example.demo.service.LoginService;

/**
 * SEGURIDAD - Servicio de autenticación con múltiples mejoras de seguridad:
 *
 * 1. HASHING BCrypt (CWE-256, CWE-916):
 *    - Se busca el usuario por correo, luego se verifica la contraseña en Java con BCrypt.
 *    - Ya no se compara la contraseña en texto plano dentro de la consulta SQL.
 *    - MIGRACIÓN TRANSPARENTE: Si la contraseña almacenada es texto plano y coincide,
 *      se rehashea automáticamente. El usuario no necesita cambiar nada.
 *
 * 2. ANTI-ENUMERACIÓN DE USUARIOS (CWE-204):
 *    - El mensaje de error es genérico tanto si el correo no existe como si la
 *      contraseña es incorrecta. Antes se distinguían ambos casos, permitiendo que
 *      un atacante supiera qué correos están registrados en el sistema.
 *
 * 3. ELIMINACIÓN DE CREDENCIALES EN LOGS (CWE-532):
 *    - Se eliminaron los System.out.println que imprimían la contraseña recibida.
 *    - Los logs usan SLF4J en lugar de System.out para ser configurables.
 *
 * 4. RATE LIMITING (CWE-307):
 *    - Integrado en LoginController.java para bloquear IPs con exceso de intentos.
 */
@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    // Mensaje genérico para evitar user enumeration
    private static final String MSG_CREDENCIALES_INVALIDAS =
            "Correo o contraseña incorrectos";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private PermisoPersonalizadoRepository permisoPersonalizadoRepository;

    @Autowired
    private PasswordUtil passwordUtil;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        log.info("Intento de login para correo: {}", sanitizarCorreo(loginRequest.getCorreo()));

        // --- Validación básica de entrada ---
        if (loginRequest.getCorreo() == null || loginRequest.getCorreo().isBlank()
                || loginRequest.getContrasena() == null || loginRequest.getContrasena().isBlank()) {
            log.warn("Intento de login con campos vacíos");
            return respuestaError(MSG_CREDENCIALES_INVALIDAS);
        }

        // --- Buscar en tabla de Usuarios ---
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(loginRequest.getCorreo().trim());

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            // Verificar contraseña con soporte BCrypt + migración transparente
            if (!passwordUtil.verificar(loginRequest.getContrasena(), usuario.getContrasena())) {
                log.warn("Contraseña incorrecta para usuario con correo: {}", sanitizarCorreo(loginRequest.getCorreo()));
                return respuestaError(MSG_CREDENCIALES_INVALIDAS);
            }

            // Verificar estado activo
            if (!"Activo".equals(usuario.getActivo())) {
                log.warn("Intento de login de usuario inactivo: {}", usuario.getMatricula());
                return respuestaError("Tu cuenta está inactiva. Contacta al administrador");
            }

            // MIGRACIÓN TRANSPARENTE: rehashear si la contraseña estaba en texto plano
            if (passwordUtil.necesitaMigracion(usuario.getContrasena())) {
                usuario.setContrasena(passwordUtil.hashear(loginRequest.getContrasena()));
                usuarioRepository.save(usuario);
                log.info("Contraseña migrada a BCrypt para matrícula: {}", usuario.getMatricula());
            }

            String nombreCompleto = usuario.getNombreUsuario() + " " +
                    usuario.getApellidoPaternoUsuario() + " " +
                    usuario.getApellidoMaternoUsuario();

            String tipoPermiso = usuario.getRol() != null ? usuario.getRol().getTipoPermiso() : null;
            List<String> accionesAdmin = null;
            if ("ADMIN".equalsIgnoreCase(tipoPermiso)) {
                accionesAdmin = permisoPersonalizadoRepository.findNombresAccionesByMatricula(usuario.getMatricula());
            }

            log.info("Login exitoso para matrícula: {}", usuario.getMatricula());
            LoginResponse respuesta = new LoginResponse();
            respuesta.setSuccess(true);
            respuesta.setMensaje("Login exitoso");
            respuesta.setMatricula(usuario.getMatricula());
            respuesta.setNombreCompleto(nombreCompleto);
            respuesta.setNombreRol(usuario.getRol() != null ? usuario.getRol().getNombreRol() : null);
            respuesta.setIdRol(usuario.getRol() != null ? usuario.getRol().getIdRol() : null);
            respuesta.setTipoPermiso(tipoPermiso);
            respuesta.setAccionesAdmin(accionesAdmin);
            respuesta.setFotoPerfil(usuario.getFotoPerfil());
            respuesta.setTipoCuenta("USUARIO");
            respuesta.setIdTutor(null);
            return respuesta;
        }

        // --- Buscar en tabla de Tutores ---
        Optional<Tutor> tutorOpt = tutorRepository.findByCorreo(loginRequest.getCorreo().trim());

        if (tutorOpt.isPresent()) {
            Tutor tutor = tutorOpt.get();

            if (!passwordUtil.verificar(loginRequest.getContrasena(), tutor.getContrasenia())) {
                log.warn("Contraseña incorrecta para tutor con correo: {}", sanitizarCorreo(loginRequest.getCorreo()));
                return respuestaError(MSG_CREDENCIALES_INVALIDAS);
            }

            // MIGRACIÓN TRANSPARENTE para tutores
            if (passwordUtil.necesitaMigracion(tutor.getContrasenia())) {
                tutor.setContrasenia(passwordUtil.hashear(loginRequest.getContrasena()));
                tutorRepository.save(tutor);
                log.info("Contraseña de tutor migrada a BCrypt para ID: {}", tutor.getIdTutor());
            }

            log.info("Login exitoso para tutor ID: {}", tutor.getIdTutor());
            LoginResponse respuesta = new LoginResponse();
            respuesta.setSuccess(true);
            respuesta.setMensaje("Login exitoso");
            respuesta.setMatricula(null);
            respuesta.setNombreCompleto(tutor.getNombre());
            respuesta.setNombreRol("Tutor");
            respuesta.setIdRol(null);
            respuesta.setTipoPermiso("TUTOR");
            respuesta.setAccionesAdmin(null);
            respuesta.setFotoPerfil(null);
            respuesta.setTipoCuenta("TUTOR");
            respuesta.setIdTutor(tutor.getIdTutor());
            return respuesta;
        }

        // Correo no encontrado — mismo mensaje genérico (anti-enumeración)
        log.warn("Intento de login con correo no registrado");
        return respuestaError(MSG_CREDENCIALES_INVALIDAS);
    }

    private LoginResponse respuestaError(String mensaje) {
        LoginResponse respuesta = new LoginResponse();
        respuesta.setSuccess(false);
        respuesta.setMensaje(mensaje);
        respuesta.setMatricula(null);
        respuesta.setNombreCompleto(null);
        respuesta.setNombreRol(null);
        respuesta.setIdRol(null);
        respuesta.setTipoPermiso(null);
        respuesta.setAccionesAdmin(null);
        respuesta.setFotoPerfil(null);
        respuesta.setTipoCuenta(null);
        respuesta.setIdTutor(null);
        return respuesta;
    }

    /** Trunca el correo en logs para no exponer el completo en archivos de log. */
    private String sanitizarCorreo(String correo) {
        if (correo == null || !correo.contains("@")) return "***";
        int at = correo.indexOf('@');
        String local = correo.substring(0, Math.min(3, at));
        return local + "***" + correo.substring(at);
    }
}
