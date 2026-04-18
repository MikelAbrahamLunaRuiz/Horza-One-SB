package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.RolDTO;

public interface RolService {
    List<RolDTO> obtenerTodos();
    RolDTO obtenerPorId(Integer id);
    RolDTO crear(RolDTO rolDTO);
    RolDTO actualizar(Integer id, RolDTO rolDTO);
    void eliminar(Integer id);
}
