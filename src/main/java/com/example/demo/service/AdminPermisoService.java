package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.AccionAdminDTO;
import com.example.demo.dto.PermisoPersonalizadoDTO;

public interface AdminPermisoService {

    List<AccionAdminDTO> obtenerAccionesAdmin();

    AccionAdminDTO crearAccionAdmin(AccionAdminDTO accionAdminDTO);

    List<PermisoPersonalizadoDTO> obtenerPermisosPorMatricula(Integer matricula);

    List<String> obtenerNombresAccionesPorMatricula(Integer matricula);

    PermisoPersonalizadoDTO asignarPermiso(Integer matricula, Integer idAccion);

    void quitarPermiso(Integer matricula, Integer idAccion);

    boolean usuarioTieneAccion(Integer matricula, String nombreAccion);
}
