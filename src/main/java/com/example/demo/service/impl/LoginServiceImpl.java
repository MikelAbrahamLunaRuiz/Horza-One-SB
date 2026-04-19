package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.model.Tutor;
import com.example.demo.model.Usuario;
import com.example.demo.respository.PermisoPersonalizadoRepository;
import com.example.demo.respository.TutorRepository;
import com.example.demo.respository.UsuarioRepository;
import com.example.demo.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private PermisoPersonalizadoRepository permisoPersonalizadoRepository;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        System.out.println("========== INTENTO DE LOGIN ==========");
        System.out.println("Correo recibido: [" + loginRequest.getCorreo() + "]");
        System.out.println("Contraseña recibida: [" + loginRequest.getContrasena() + "]");
        
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreoAndContrasena(
                loginRequest.getCorreo(),
                loginRequest.getContrasena()
        );

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            // Verificar si el usuario está activo
            if (!"Activo".equals(usuario.getActivo())) {
                System.out.println("❌ Usuario INACTIVO");
                return respuestaError("Tu cuenta está inactiva. Contacta al administrador");
            }

            String nombreCompleto = usuario.getNombreUsuario() + " " +
                    usuario.getApellidoPaternoUsuario() + " " +
                    usuario.getApellidoMaternoUsuario();

            String tipoPermiso = usuario.getRol() != null ? usuario.getRol().getTipoPermiso() : null;
            List<String> accionesAdmin = null;
            if ("ADMIN".equalsIgnoreCase(tipoPermiso)) {
                accionesAdmin = permisoPersonalizadoRepository.findNombresAccionesByMatricula(usuario.getMatricula());
            }

            System.out.println("✅ LOGIN EXITOSO - " + nombreCompleto);
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

        Optional<Tutor> tutorOpt = tutorRepository.findByCorreoAndContrasenia(
                loginRequest.getCorreo(),
                loginRequest.getContrasena()
        );
        if (tutorOpt.isPresent()) {
            Tutor tutor = tutorOpt.get();
            System.out.println("✅ LOGIN EXITOSO TUTOR - ID: " + tutor.getIdTutor());
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

        boolean correoEnUsuarios = usuarioRepository.findByCorreo(loginRequest.getCorreo()).isPresent();
        boolean correoEnTutores = tutorRepository.findByCorreo(loginRequest.getCorreo()).isPresent();

        if (correoEnUsuarios || correoEnTutores) {
            System.out.println("❌ Contraseña INCORRECTA");
            return respuestaError("La contraseña es incorrecta");
        }

        System.out.println("❌ Correo NO encontrado en BD");
        return respuestaError("El correo no está registrado en el sistema");
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
}
