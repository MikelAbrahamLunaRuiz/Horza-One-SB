package com.example.demo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.model.Usuario;
import com.example.demo.respository.UsuarioRepository;
import com.example.demo.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        System.out.println("========== INTENTO DE LOGIN ==========");
        System.out.println("Correo recibido: [" + loginRequest.getCorreo() + "]");
        System.out.println("Contraseña recibida: [" + loginRequest.getContrasena() + "]");
        
        // Primero verificar si el correo existe
        Optional<Usuario> usuarioPorCorreo = usuarioRepository.findByCorreo(loginRequest.getCorreo());

        if (!usuarioPorCorreo.isPresent()) {
            System.out.println("❌ Correo NO encontrado en BD");
            // El correo no existe en la base de datos
            return new LoginResponse(
                    false,
                    "El correo no está registrado en el sistema",
                    null,
                    null,
                    null,
                    null,
                    null
            );
        }
        
        Usuario usuarioEncontrado = usuarioPorCorreo.get();
        System.out.println("✅ Correo encontrado - Matrícula: " + usuarioEncontrado.getMatricula());
        System.out.println("Contraseña en BD: [" + usuarioEncontrado.getContrasena() + "]");
        System.out.println("Estado: " + usuarioEncontrado.getActivo());

        // El correo existe, ahora verificar la contraseña
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreoAndContrasena(
                loginRequest.getCorreo(),
                loginRequest.getContrasena()
        );

        if (usuarioOpt.isPresent()) {
            System.out.println("✅ Contraseña CORRECTA");
            Usuario usuario = usuarioOpt.get();

            // Verificar si el usuario está activo
            if (!"Activo".equals(usuario.getActivo())) {
                System.out.println("❌ Usuario INACTIVO");
                return new LoginResponse(
                        false,
                        "Tu cuenta está inactiva. Contacta al administrador",
                        null,
                        null,
                        null,
                        null,
                        null
                );
            }

            String nombreCompleto = usuario.getNombreUsuario() + " " +
                    usuario.getApellidoPaternoUsuario() + " " +
                    usuario.getApellidoMaternoUsuario();

            System.out.println("✅ LOGIN EXITOSO - " + nombreCompleto);
            return new LoginResponse(
                    true,
                    "Login exitoso",
                    usuario.getMatricula(),
                    nombreCompleto,
                    usuario.getRol().getNombreRol(),
                    usuario.getRol().getIdRol(),
                    usuario.getRol().getTipoPermiso()
            );
        } else {
            System.out.println("❌ Contraseña INCORRECTA");
            // El correo existe pero la contraseña es incorrecta
            return new LoginResponse(
                    false,
                    "La contraseña es incorrecta",
                    null,
                    null,
                    null,
                    null,
                    null
            );
        }
    }
}