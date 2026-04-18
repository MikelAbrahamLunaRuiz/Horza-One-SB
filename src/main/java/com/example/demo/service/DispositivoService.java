package com.example.demo.service;

import com.example.demo.dto.DispositivoDTO;
import java.util.List;

public interface DispositivoService {
    List<DispositivoDTO> obtenerTodos();
    DispositivoDTO obtenerPorId(Integer id);
    List<DispositivoDTO> obtenerPorArea(Integer idArea);
    List<DispositivoDTO> obtenerDispositivosInactivos();
    DispositivoDTO crear(DispositivoDTO dispositivoDTO);
    DispositivoDTO actualizar(Integer id, DispositivoDTO dispositivoDTO);
    DispositivoDTO cambiarEstado(Integer id, String nuevoEstado);
    void eliminar(Integer id);
}
