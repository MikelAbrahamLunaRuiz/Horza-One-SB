package com.example.demo.service;

import com.example.demo.dto.PermisoDiaDTO;
import com.example.demo.dto.PermisoDiaRequest;

import java.time.LocalDate;
import java.util.List;

public interface PermisoDiaService {

    // Obtener todos los permisos activos
    List<PermisoDiaDTO> obtenerTodosLosPermisos();

    // Obtener permiso por ID
    PermisoDiaDTO obtenerPermisoPorId(Integer idPermiso);

    // Obtener permisos generales
    List<PermisoDiaDTO> obtenerPermisosGenerales();

    // Obtener permisos de un usuario específico
    List<PermisoDiaDTO> obtenerPermisosPorUsuario(Integer matricula);

    // Obtener permisos por fecha
    List<PermisoDiaDTO> obtenerPermisosPorFecha(LocalDate fecha);

    // Obtener permisos por rango de fechas
    List<PermisoDiaDTO> obtenerPermisosPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin);

    // Obtener permisos por tipo
    List<PermisoDiaDTO> obtenerPermisosPorTipo(String tipoPermiso);

    // Crear nuevo permiso
    PermisoDiaDTO crearPermiso(PermisoDiaRequest request);

    // Actualizar permiso existente
    PermisoDiaDTO actualizarPermiso(Integer idPermiso, PermisoDiaRequest request);

    // Eliminar permiso (soft delete - cambiar a Inactivo)
    void eliminarPermiso(Integer idPermiso);

    // Verificar si un usuario tiene permiso en una fecha
    boolean usuarioTienePermisoEnFecha(Integer matricula, LocalDate fecha);
}
