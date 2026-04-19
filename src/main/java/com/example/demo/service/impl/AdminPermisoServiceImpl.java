package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AccionAdminDTO;
import com.example.demo.dto.PermisoPersonalizadoDTO;
import com.example.demo.model.AccionAdmin;
import com.example.demo.model.PermisoPersonalizado;
import com.example.demo.model.Usuario;
import com.example.demo.respository.AccionAdminRepository;
import com.example.demo.respository.PermisoPersonalizadoRepository;
import com.example.demo.respository.UsuarioRepository;
import com.example.demo.service.AdminPermisoService;

@Service
public class AdminPermisoServiceImpl implements AdminPermisoService {

    @Autowired
    private AccionAdminRepository accionAdminRepository;

    @Autowired
    private PermisoPersonalizadoRepository permisoPersonalizadoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<AccionAdminDTO> obtenerAccionesAdmin() {
        return accionAdminRepository.findAll().stream()
                .map(this::convertirAccionADTO)
                .collect(Collectors.toList());
    }

    @Override
    public AccionAdminDTO crearAccionAdmin(AccionAdminDTO accionAdminDTO) {
        if (accionAdminDTO.getNombreAccion() == null || accionAdminDTO.getNombreAccion().trim().isEmpty()) {
            throw new RuntimeException("El nombre de la acción es obligatorio");
        }

        accionAdminRepository.findByNombreAccion(accionAdminDTO.getNombreAccion().trim())
                .ifPresent(a -> {
                    throw new RuntimeException("La acción ya existe: " + a.getNombreAccion());
                });

        AccionAdmin accionAdmin = new AccionAdmin();
        accionAdmin.setNombreAccion(accionAdminDTO.getNombreAccion().trim());

        return convertirAccionADTO(accionAdminRepository.save(accionAdmin));
    }

    @Override
    public List<PermisoPersonalizadoDTO> obtenerPermisosPorMatricula(Integer matricula) {
        validarUsuarioExisteYEsAdmin(matricula);

        return permisoPersonalizadoRepository.findByMatricula(matricula).stream()
                .map(this::convertirPermisoADTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> obtenerNombresAccionesPorMatricula(Integer matricula) {
        validarUsuarioExisteYEsAdmin(matricula);
        return permisoPersonalizadoRepository.findNombresAccionesByMatricula(matricula);
    }

    @Override
    public PermisoPersonalizadoDTO asignarPermiso(Integer matricula, Integer idAccion) {
        validarUsuarioExisteYEsAdmin(matricula);

        AccionAdmin accionAdmin = accionAdminRepository.findById(idAccion)
                .orElseThrow(() -> new RuntimeException("Acción no encontrada con ID: " + idAccion));

        if (permisoPersonalizadoRepository.existsByMatriculaAndIdAccion(matricula, idAccion)) {
            throw new RuntimeException("El permiso ya está asignado a la matrícula " + matricula);
        }

        PermisoPersonalizado permiso = new PermisoPersonalizado(matricula, idAccion);
        PermisoPersonalizado permisoGuardado = permisoPersonalizadoRepository.save(permiso);

        PermisoPersonalizadoDTO dto = convertirPermisoADTO(permisoGuardado);
        if (dto.getNombreAccion() == null) {
            dto.setNombreAccion(accionAdmin.getNombreAccion());
        }
        return dto;
    }

    @Override
    public void quitarPermiso(Integer matricula, Integer idAccion) {
        validarUsuarioExisteYEsAdmin(matricula);

        if (!permisoPersonalizadoRepository.existsByMatriculaAndIdAccion(matricula, idAccion)) {
            throw new RuntimeException("El permiso no existe para la matrícula indicada");
        }

        permisoPersonalizadoRepository.deleteByMatriculaAndIdAccion(matricula, idAccion);
    }

    @Override
    public boolean usuarioTieneAccion(Integer matricula, String nombreAccion) {
        if (nombreAccion == null || nombreAccion.trim().isEmpty()) {
            return false;
        }

        Usuario usuario = usuarioRepository.findById(matricula).orElse(null);
        if (usuario == null || usuario.getRol() == null || !"ADMIN".equalsIgnoreCase(usuario.getRol().getTipoPermiso())) {
            return false;
        }

        List<String> acciones = permisoPersonalizadoRepository.findNombresAccionesByMatricula(matricula);
        return acciones.stream().anyMatch(a -> nombreAccion.trim().equalsIgnoreCase(a));
    }

    private void validarUsuarioExisteYEsAdmin(Integer matricula) {
        Usuario usuario = usuarioRepository.findById(matricula)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con matrícula: " + matricula));

        if (usuario.getRol() == null || !"ADMIN".equalsIgnoreCase(usuario.getRol().getTipoPermiso())) {
            throw new RuntimeException("La matrícula indicada no pertenece a un perfil administrativo");
        }
    }

    private AccionAdminDTO convertirAccionADTO(AccionAdmin accionAdmin) {
        return new AccionAdminDTO(accionAdmin.getIdAccion(), accionAdmin.getNombreAccion());
    }

    private PermisoPersonalizadoDTO convertirPermisoADTO(PermisoPersonalizado permiso) {
        String nombreAccion = null;
        if (permiso.getAccionAdmin() != null) {
            nombreAccion = permiso.getAccionAdmin().getNombreAccion();
        } else {
            nombreAccion = accionAdminRepository.findById(permiso.getIdAccion())
                    .map(AccionAdmin::getNombreAccion)
                    .orElse(null);
        }

        return new PermisoPersonalizadoDTO(
                permiso.getMatricula(),
                permiso.getIdAccion(),
                nombreAccion
        );
    }
}
