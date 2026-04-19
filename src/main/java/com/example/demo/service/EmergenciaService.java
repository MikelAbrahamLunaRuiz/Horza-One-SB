package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.UsuarioEmergenciaDTO;

public interface EmergenciaService {
    List<UsuarioEmergenciaDTO> obtenerUsuariosDentro();
    List<UsuarioEmergenciaDTO> obtenerUsuariosDentroPorTutor(Integer idTutor);
    byte[] exportarUsuariosDentroExcel() throws Exception;
}
