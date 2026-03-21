package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.BitacoraDTO;

public interface BitacoraService {
    List<BitacoraDTO> obtenerTodas();
    BitacoraDTO obtenerPorId(Integer id);
    BitacoraDTO crear(BitacoraDTO bitacoraDTO);
    BitacoraDTO actualizar(Integer id, BitacoraDTO bitacoraDTO);
    void eliminar(Integer id);
}
