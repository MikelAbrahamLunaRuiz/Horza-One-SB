package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.AreaDTO;

public interface AreaService {
    List<AreaDTO> obtenerTodas();
    AreaDTO obtenerPorId(Integer id);
    AreaDTO crear(AreaDTO areaDTO);
    AreaDTO actualizar(Integer id, AreaDTO areaDTO);
    void eliminar(Integer id);
}
