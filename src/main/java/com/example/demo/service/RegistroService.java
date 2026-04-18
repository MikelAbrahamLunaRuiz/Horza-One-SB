package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.ConsultaRegistrosRequest;
import com.example.demo.dto.ConsultaRegistrosResponse;
import com.example.demo.dto.RegistroDTO;

public interface RegistroService {
    List<RegistroDTO> obtenerTodos();
    RegistroDTO obtenerPorId(Integer id);
    RegistroDTO crear(RegistroDTO registroDTO);
    RegistroDTO actualizar(Integer id, RegistroDTO registroDTO);
    void eliminar(Integer id);
    ConsultaRegistrosResponse consultarRegistrosPorFiltros(ConsultaRegistrosRequest request);
    List<RegistroDTO> obtenerUltimos3PorDispositivo(Integer idDispositivo);
    List<RegistroDTO> obtenerTodosPorDispositivo(Integer idDispositivo);
    RegistroDTO obtenerUltimoRegistroUsuario(Integer matricula);
    byte[] exportarBitacoraExcel(ConsultaRegistrosRequest request) throws Exception;
}
