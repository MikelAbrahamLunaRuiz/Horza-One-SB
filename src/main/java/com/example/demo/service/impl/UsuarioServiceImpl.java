package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BajaUsuarioRequest;
import com.example.demo.dto.CambioContrasenaRequest;
import com.example.demo.dto.UsuarioDTO;
import com.example.demo.model.Calendario;
import com.example.demo.model.Rol;
import com.example.demo.model.Usuario;
import com.example.demo.model.UsuarioCalendario;
import com.example.demo.model.UsuarioCalendarioId;
import com.example.demo.respository.CalendarioRepository;
import com.example.demo.respository.RolRepository;
import com.example.demo.respository.UsuarioCalendarioRepository;
import com.example.demo.respository.UsuarioRepository;
import com.example.demo.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;
    
    @Autowired
    private CalendarioRepository calendarioRepository;
    
    @Autowired
    private UsuarioCalendarioRepository usuarioCalendarioRepository;

    @Override
    public List<UsuarioDTO> obtenerTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO obtenerPorId(Integer matricula) {
        Usuario usuario = usuarioRepository.findById(matricula)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return convertirADTO(usuario);
    }

    @Override
    public UsuarioDTO crear(UsuarioDTO usuarioDTO) {
        // Validar que la matrícula no exista
        if (usuarioRepository.existsById(usuarioDTO.getMatricula())) {
            throw new RuntimeException("La matrícula " + usuarioDTO.getMatricula() + " ya existe en el sistema");
        }
        
        // Crear el usuario
        Usuario usuario = convertirAEntidad(usuarioDTO);
        
        // IMPORTANTE: Asegurar que estado_presencia se inicialice correctamente
        if (usuario.getEstadoPresencia() == null || usuario.getEstadoPresencia().isEmpty()) {
            usuario.setEstadoPresencia("Fuera");
            System.out.println("✅ Estado de presencia inicializado a 'Fuera' para nuevo usuario: " + usuario.getMatricula());
        }
        
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        
        // Si viene idCalendario, crear la relación en USUARIOS_CALENDARIO
        if (usuarioDTO.getIdCalendario() != null) {
            Calendario calendario = calendarioRepository.findById(usuarioDTO.getIdCalendario())
                    .orElseThrow(() -> new RuntimeException("Calendario no encontrado con ID: " + usuarioDTO.getIdCalendario()));
            
            UsuarioCalendarioId ucId = new UsuarioCalendarioId(usuarioGuardado.getMatricula(), calendario.getIdCalendario());
            UsuarioCalendario usuarioCalendario = new UsuarioCalendario();
            usuarioCalendario.setId(ucId);
            usuarioCalendario.setUsuario(usuarioGuardado);
            usuarioCalendario.setCalendario(calendario);
            
            usuarioCalendarioRepository.save(usuarioCalendario);
        }
        
        return convertirADTO(usuarioGuardado);
    }

    @Override
    public UsuarioDTO actualizar(Integer matricula, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(matricula)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Rol rol = rolRepository.findById(usuarioDTO.getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        usuario.setRol(rol);
        usuario.setRfc(usuarioDTO.getRfc());
        usuario.setCurp(usuarioDTO.getCurp());
        usuario.setFechaAlta(usuarioDTO.getFechaAlta());
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuario.setApellidoPaternoUsuario(usuarioDTO.getApellidoPaternoUsuario());
        usuario.setApellidoMaternoUsuario(usuarioDTO.getApellidoMaternoUsuario());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setTipoContrato(usuarioDTO.getTipoContrato());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setActivo(usuarioDTO.getActivo());
        usuario.setCpUsuario(usuarioDTO.getCpUsuario());
        usuario.setCalleUsuario(usuarioDTO.getCalleUsuario());
        
        // Solo actualizar contraseña si viene en el DTO (no null y no vacía)
        if (usuarioDTO.getContrasena() != null && !usuarioDTO.getContrasena().isEmpty()) {
            usuario.setContrasena(usuarioDTO.getContrasena());
        }

        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return convertirADTO(usuarioActualizado);
    }

    @Override
    public void eliminar(Integer matricula) {
        usuarioRepository.deleteById(matricula);
    }

    @Override
    public boolean eliminarConValidacion(BajaUsuarioRequest request) {
        // Verificar que el usuario a eliminar existe
        Usuario usuarioAEliminar = usuarioRepository.findById(request.getMatricula())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con matrícula: " + request.getMatricula()));
        
        // Verificar que el administrador existe y su contraseña es correcta
        Usuario admin = usuarioRepository.findById(request.getMatriculaAdmin())
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));
        
        if (!admin.getContrasena().equals(request.getContrasenaAdmin())) {
            return false; // Contraseña incorrecta
        }
        
        // Verificar que el admin tiene rol de administrador (id_rol = 1)
        if (admin.getRol().getIdRol() != 1) {
            throw new RuntimeException("Solo los administradores pueden eliminar usuarios");
        }
        
        // Eliminar el usuario
        usuarioRepository.deleteById(request.getMatricula());
        return true;
    }

    @Override
    public boolean cambiarContrasena(CambioContrasenaRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getMatricula())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuario.getContrasena().equals(request.getContrasenaActual())) {
            return false;
        }

        usuario.setContrasena(request.getContrasenaNueva());
        usuarioRepository.save(usuario);
        return true;
    }

    private UsuarioDTO convertirADTO(Usuario usuario) {
        // Obtener el calendario asignado desde USUARIOS_CALENDARIO
        Integer idCalendario = null;
        List<UsuarioCalendario> asignaciones = usuarioCalendarioRepository.findById_Matricula(usuario.getMatricula());
        if (!asignaciones.isEmpty()) {
            idCalendario = asignaciones.get(0).getCalendario().getIdCalendario();
        }
        
        return new UsuarioDTO(
                usuario.getMatricula(),
                usuario.getRol().getIdRol(),
                idCalendario, // idCalendario obtenido de USUARIOS_CALENDARIO
                usuario.getRfc(),
                usuario.getCurp(),
                usuario.getFechaAlta(),
                usuario.getNombreUsuario(),
                usuario.getApellidoPaternoUsuario(),
                usuario.getApellidoMaternoUsuario(),
                usuario.getTelefono(),
                usuario.getTipoContrato(),
                usuario.getCorreo(),
                usuario.getActivo(),
                usuario.getCpUsuario(),
                usuario.getCalleUsuario(),
                null // No devolver la contraseña por seguridad
        );
    }

    private Usuario convertirAEntidad(UsuarioDTO dto) {
        Rol rol = rolRepository.findById(dto.getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        return new Usuario(
                dto.getMatricula(),
                rol,
                dto.getRfc(),
                dto.getCurp(),
                dto.getFechaAlta(),
                dto.getNombreUsuario(),
                dto.getApellidoPaternoUsuario(),
                dto.getApellidoMaternoUsuario(),
                dto.getTelefono(),
                dto.getTipoContrato(),
                dto.getCorreo(),
                dto.getActivo(),
                dto.getCpUsuario(),
                dto.getCalleUsuario(),
                dto.getContrasena()
        );
    }
}