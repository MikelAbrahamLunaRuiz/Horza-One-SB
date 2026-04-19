package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.ExpedienteDigitalDTO;

public interface ExpedienteDigitalService {

    List<ExpedienteDigitalDTO> obtenerTodos();

    List<ExpedienteDigitalDTO> obtenerPorMatricula(Integer matricula);

    ExpedienteDigitalDTO crear(ExpedienteDigitalDTO expedienteDigitalDTO);

    void eliminar(Integer idArchivo);
}
