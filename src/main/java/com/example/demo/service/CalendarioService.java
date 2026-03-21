package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.CalendarioConHorarios;
import com.example.demo.dto.CalendarioCreateDTO;
import com.example.demo.dto.CalendarioDTO;

public interface CalendarioService {
    List<CalendarioDTO> obtenerTodos();
    CalendarioDTO obtenerPorId(Integer id);
    CalendarioDTO crear(CalendarioCreateDTO calendarioDTO);
    CalendarioDTO actualizar(Integer id, CalendarioCreateDTO calendarioDTO);
    void eliminar(Integer id);
    CalendarioDTO cambiarEstado(Integer id, String nuevoEstado);
    CalendarioConHorarios obtenerCalendarioConHorarios(Integer idCalendario);
}
