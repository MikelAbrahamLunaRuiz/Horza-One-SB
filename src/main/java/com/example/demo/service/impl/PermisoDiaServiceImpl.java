package com.example.demo.service.impl;

import com.example.demo.dto.PermisoDiaDTO;
import com.example.demo.dto.PermisoDiaRequest;
import com.example.demo.model.PermisoDia;
import com.example.demo.model.Usuario;
import com.example.demo.respository.PermisoDiaRepository;
import com.example.demo.respository.UsuarioRepository;
import com.example.demo.service.PermisoDiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermisoDiaServiceImpl implements PermisoDiaService {

    @Autowired
    private PermisoDiaRepository permisoDiaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<PermisoDiaDTO> obtenerTodosLosPermisos() {
        return permisoDiaRepository.findByActivoOrderByFechaDesc("Activo").stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public PermisoDiaDTO obtenerPermisoPorId(Integer idPermiso) {
        PermisoDia permiso = permisoDiaRepository.findById(idPermiso)
                .orElseThrow(() -> new RuntimeException("Permiso no encontrado con ID: " + idPermiso));
        return convertirADTO(permiso);
    }

    @Override
    public List<PermisoDiaDTO> obtenerPermisosGenerales() {
        return permisoDiaRepository.findByEsGeneralAndActivoOrderByFecha(true, "Activo").stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermisoDiaDTO> obtenerPermisosPorUsuario(Integer matricula) {
        return permisoDiaRepository.findByMatriculaAndActivoOrderByFecha(matricula, "Activo").stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermisoDiaDTO> obtenerPermisosPorFecha(LocalDate fecha) {
        return permisoDiaRepository.findByFechaAndActivo(fecha, "Activo").stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermisoDiaDTO> obtenerPermisosPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return permisoDiaRepository.findByFechaBetween(fechaInicio, fechaFin, "Activo").stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermisoDiaDTO> obtenerPermisosPorTipo(String tipoPermiso) {
        return permisoDiaRepository.findByTipoPermisoAndActivoOrderByFecha(tipoPermiso, "Activo").stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public PermisoDiaDTO crearPermiso(PermisoDiaRequest request) {
        // Validar request
        if (!request.isValid()) {
            throw new RuntimeException("Datos del permiso inválidos");
        }

        // Si es específico, validar que el usuario existe
        if (Boolean.FALSE.equals(request.getEsGeneral())) {
            usuarioRepository.findById(request.getMatricula())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con matrícula: " + request.getMatricula()));
        }

        // Crear nueva entidad
        PermisoDia permiso = new PermisoDia();
        permiso.setTipoPermiso(request.getTipoPermiso());
        permiso.setFecha(request.getFecha());
        permiso.setDescripcion(request.getDescripcion());
        permiso.setEsGeneral(request.getEsGeneral());
        permiso.setMatricula(request.getMatricula());
        permiso.setActivo("Activo");

        PermisoDia permisoGuardado = permisoDiaRepository.save(permiso);
        return convertirADTO(permisoGuardado);
    }

    @Override
    public PermisoDiaDTO actualizarPermiso(Integer idPermiso, PermisoDiaRequest request) {
        // Validar request
        if (!request.isValid()) {
            throw new RuntimeException("Datos del permiso inválidos");
        }

        // Buscar permiso existente
        PermisoDia permiso = permisoDiaRepository.findById(idPermiso)
                .orElseThrow(() -> new RuntimeException("Permiso no encontrado con ID: " + idPermiso));

        // Si es específico, validar que el usuario existe
        if (Boolean.FALSE.equals(request.getEsGeneral())) {
            usuarioRepository.findById(request.getMatricula())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con matrícula: " + request.getMatricula()));
        }

        // Actualizar campos
        permiso.setTipoPermiso(request.getTipoPermiso());
        permiso.setFecha(request.getFecha());
        permiso.setDescripcion(request.getDescripcion());
        permiso.setEsGeneral(request.getEsGeneral());
        permiso.setMatricula(request.getMatricula());

        PermisoDia permisoActualizado = permisoDiaRepository.save(permiso);
        return convertirADTO(permisoActualizado);
    }

    @Override
    public void eliminarPermiso(Integer idPermiso) {
        PermisoDia permiso = permisoDiaRepository.findById(idPermiso)
                .orElseThrow(() -> new RuntimeException("Permiso no encontrado con ID: " + idPermiso));
        
        // Soft delete - cambiar estado a Inactivo
        permiso.setActivo("Inactivo");
        permisoDiaRepository.save(permiso);
    }

    @Override
    public boolean usuarioTienePermisoEnFecha(Integer matricula, LocalDate fecha) {
        // Verificar si hay un permiso general para esa fecha
        boolean permisoGeneral = permisoDiaRepository.existsByFechaAndEsGeneralAndActivo(fecha, true, "Activo");
        
        // Verificar si el usuario específico tiene permiso
        boolean permisoEspecifico = permisoDiaRepository.existsByFechaAndMatriculaAndActivo(fecha, matricula, "Activo");
        
        return permisoGeneral || permisoEspecifico;
    }

    // Método auxiliar para convertir entidad a DTO
    private PermisoDiaDTO convertirADTO(PermisoDia permiso) {
        PermisoDiaDTO dto = new PermisoDiaDTO();
        dto.setIdPermiso(permiso.getIdPermiso());
        dto.setTipoPermiso(permiso.getTipoPermiso());
        dto.setFecha(permiso.getFecha());
        dto.setDescripcion(permiso.getDescripcion());
        dto.setEsGeneral(permiso.getEsGeneral());
        dto.setMatricula(permiso.getMatricula());
        dto.setActivo(permiso.getActivo());

        // Si es específico, obtener nombre del usuario
        if (Boolean.FALSE.equals(permiso.getEsGeneral()) && permiso.getMatricula() != null) {
            usuarioRepository.findById(permiso.getMatricula()).ifPresent(usuario -> {
                String nombreCompleto = usuario.getNombreUsuario() + " " +
                        usuario.getApellidoPaternoUsuario() + " " +
                        usuario.getApellidoMaternoUsuario();
                dto.setNombreCompleto(nombreCompleto);
            });
        } else {
            dto.setNombreCompleto("TODOS LOS USUARIOS");
        }

        return dto;
    }
}
