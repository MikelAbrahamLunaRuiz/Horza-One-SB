package com.example.demo.controller;

import com.example.demo.dto.EstadisticasDiariaDTO;
import com.example.demo.dto.EstadisticasMensualDTO;
import com.example.demo.service.impl.EstadisticasServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/estadisticas")
@CrossOrigin(origins = "*")
public class EstadisticasController {

    @Autowired
    private EstadisticasServiceImpl estadisticasService;

    /**
     * Obtener estadísticas del día
     * GET /api/estadisticas/diarias?fecha=2025-01-15
     * Si no se proporciona fecha, usa la fecha actual
     */
    @GetMapping("/diarias")
    public ResponseEntity<EstadisticasDiariaDTO> obtenerEstadisticasDiarias(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

        if (fecha == null) {
            fecha = LocalDate.now();
        }

        return ResponseEntity.ok(estadisticasService.obtenerEstadisticasDelDia(fecha));
    }

    /**
     * Obtener estadísticas mensuales
     * GET /api/estadisticas/mensuales?mes=1&anio=2025&tipo=puntuales
     * Tipos válidos: puntuales, retardos, inasistencias, permisos
     */
    @GetMapping("/mensuales")
    public ResponseEntity<EstadisticasMensualDTO> obtenerEstadisticasMensuales(
            @RequestParam Integer mes,
            @RequestParam Integer anio,
            @RequestParam String tipo) {

        return ResponseEntity.ok(estadisticasService.obtenerEstadisticasMensuales(mes, anio, tipo));
    }
}