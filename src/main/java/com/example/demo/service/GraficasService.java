package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.dto.AsistenciaPorAreaDTO;
import com.example.demo.dto.EntradasSalidasSemanaDTO;
import com.example.demo.dto.RegistrosPorDiaDTO;

public interface GraficasService {
    
    /**
     * GRÁFICA 1: Registros por día (Line Chart)
     * Retorna la actividad diaria de registros en un rango de fechas
     */
    List<RegistrosPorDiaDTO> obtenerRegistrosPorDia(LocalDate fechaInicio, LocalDate fechaFin);
    
    /**
     * GRÁFICA 2: Entradas vs Salidas por semana (Bar Chart)
     * Retorna comparativa semanal de entradas y salidas
     */
    List<EntradasSalidasSemanaDTO> obtenerEntradasSalidasPorSemana(LocalDate fechaInicio, LocalDate fechaFin);
    
    /**
     * GRÁFICA 3: Asistencia por áreas en un mes (Pie Chart)
     * Retorna la distribución de asistencias por área en un mes específico
     */
    List<AsistenciaPorAreaDTO> obtenerAsistenciaPorArea(Integer mes, Integer anio);
    
    /**
     * GRÁFICA 3B: Asistencia por áreas en un rango de fechas (Pie Chart)
     * Retorna la distribución de asistencias por área en un rango personalizado
     */
    List<AsistenciaPorAreaDTO> obtenerAsistenciaPorAreaRango(LocalDate fechaInicio, LocalDate fechaFin);
}
