package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.HorarioConBloques;
import com.example.demo.dto.HorarioCreateDTO;
import com.example.demo.dto.HorarioDTO;
import com.example.demo.dto.HorarioSemanalCreateDTO;
import com.example.demo.dto.HorarioSemanalResponseDTO;

public interface HorarioService {
    List<HorarioDTO> obtenerTodos();
    HorarioDTO obtenerPorId(Integer id);
    List<HorarioDTO> obtenerPorCalendario(Integer idCalendario);
    HorarioDTO crear(HorarioCreateDTO horarioDTO);
    HorarioDTO actualizar(Integer id, HorarioCreateDTO horarioDTO);
    void eliminar(Integer id);
    HorarioDTO cambiarEstado(Integer id, String nuevoEstado);
    HorarioConBloques obtenerHorarioConBloques(Integer idHorario);
    
    /**
     * Obtiene todos los horarios con su estructura completa de 7 días y bloques
     */
    List<HorarioConBloques> obtenerTodosConBloques();
    
    /**
     * Crea un horario semanal completo (7 registros HORARIO + sus BLOQUES_HORARIO)
     */
    HorarioSemanalResponseDTO crearHorarioSemanal(HorarioSemanalCreateDTO dto);
    
    /**
     * Actualiza un horario semanal completo (modifica nombre, descripción, estado y bloques)
     */
    HorarioSemanalResponseDTO actualizarHorarioSemanal(Integer id, HorarioSemanalCreateDTO dto);
}
