package com.example.demo.service;

import com.example.demo.dto.BloqueHorarioCreateDTO;
import com.example.demo.dto.BloqueHorarioDTO;
import java.util.List;

public interface BloqueHorarioService {
    List<BloqueHorarioDTO> obtenerTodos();
    List<BloqueHorarioDTO> obtenerPlantillas();
    BloqueHorarioDTO obtenerPorId(Integer id);
    List<BloqueHorarioDTO> obtenerPorHorario(Integer idHorario);
    BloqueHorarioDTO crear(BloqueHorarioCreateDTO bloqueHorarioDTO);
    BloqueHorarioDTO actualizar(Integer id, BloqueHorarioCreateDTO bloqueHorarioDTO);
    void eliminar(Integer id);
}
