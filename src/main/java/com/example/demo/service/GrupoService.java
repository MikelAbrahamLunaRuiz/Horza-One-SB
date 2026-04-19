package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.GrupoDTO;

public interface GrupoService {

    List<GrupoDTO> obtenerTodos();

    GrupoDTO obtenerPorId(Integer idGrupo);

    GrupoDTO crear(GrupoDTO grupoDTO);

    GrupoDTO actualizar(Integer idGrupo, GrupoDTO grupoDTO);

    void eliminar(Integer idGrupo);

    List<GrupoDTO> obtenerPorLider(Integer matriculaLider);
}
