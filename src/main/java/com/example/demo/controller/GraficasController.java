package com.example.demo.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AsistenciaPorAreaDTO;
import com.example.demo.dto.EntradasSalidasSemanaDTO;
import com.example.demo.dto.RegistrosPorDiaDTO;
import com.example.demo.service.GraficasService;

@RestController
@RequestMapping("/api/graficas")
@CrossOrigin(origins = "*")
public class GraficasController {

    @Autowired
    private GraficasService graficasService;

    /**
     * ENDPOINT 1: Registros por día (Line Chart)
     * GET /api/graficas/registros-por-dia?fechaInicio=2025-01-01&fechaFin=2025-01-31
     */
    @GetMapping("/registros-por-dia")
    public ResponseEntity<?> obtenerRegistrosPorDia(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        
        try {
            System.out.println("📊 ENDPOINT: /api/graficas/registros-por-dia");
            System.out.println("   Parámetros: fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin);
            
            // Validar fechas
            if (fechaInicio.isAfter(fechaFin)) {
                return ResponseEntity.badRequest().body(
                    crearRespuestaError("La fecha de inicio no puede ser posterior a la fecha de fin")
                );
            }
            
            List<RegistrosPorDiaDTO> datos = graficasService.obtenerRegistrosPorDia(fechaInicio, fechaFin);
            
            // Crear respuesta estructurada
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("titulo", "Actividad Diaria de Registros");
            respuesta.put("tipo", "LINE_CHART");
            respuesta.put("periodo", "DIARIO");
            respuesta.put("fechaInicio", fechaInicio);
            respuesta.put("fechaFin", fechaFin);
            respuesta.put("datos", datos);
            respuesta.put("metadata", crearMetadataRegistrosPorDia(datos));
            
            System.out.println("✅ Respuesta generada con " + datos.size() + " días");
            return ResponseEntity.ok(respuesta);
            
        } catch (Exception e) {
            System.err.println("❌ Error al obtener registros por día: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(crearRespuestaError("Error al procesar la solicitud: " + e.getMessage()));
        }
    }

    /**
     * ENDPOINT 2: Entradas vs Salidas por semana (Bar Chart)
     * GET /api/graficas/entradas-salidas-semana?fechaInicio=2025-01-01&fechaFin=2025-01-31
     */
    @GetMapping("/entradas-salidas-semana")
    public ResponseEntity<?> obtenerEntradasSalidasPorSemana(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        
        try {
            System.out.println("📊 ENDPOINT: /api/graficas/entradas-salidas-semana");
            System.out.println("   Parámetros: fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin);
            
            if (fechaInicio.isAfter(fechaFin)) {
                return ResponseEntity.badRequest().body(
                    crearRespuestaError("La fecha de inicio no puede ser posterior a la fecha de fin")
                );
            }
            
            List<EntradasSalidasSemanaDTO> datos = graficasService.obtenerEntradasSalidasPorSemana(fechaInicio, fechaFin);
            
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("titulo", "Comparativa Semanal: Entradas vs Salidas");
            respuesta.put("tipo", "BAR_CHART");
            respuesta.put("periodo", "SEMANAL");
            respuesta.put("fechaInicio", fechaInicio);
            respuesta.put("fechaFin", fechaFin);
            respuesta.put("datos", datos);
            respuesta.put("metadata", crearMetadataEntradasSalidasSemana(datos));
            
            System.out.println("✅ Respuesta generada con " + datos.size() + " semanas");
            return ResponseEntity.ok(respuesta);
            
        } catch (Exception e) {
            System.err.println("❌ Error al obtener entradas/salidas por semana: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(crearRespuestaError("Error al procesar la solicitud: " + e.getMessage()));
        }
    }

    /**
     * ENDPOINT 3: Asistencia por áreas en un mes (Pie Chart)
     * GET /api/graficas/asistencia-por-area?mes=1&anio=2025
     */
    @GetMapping("/asistencia-por-area")
    public ResponseEntity<?> obtenerAsistenciaPorArea(
            @RequestParam Integer mes,
            @RequestParam Integer anio) {
        
        try {
            System.out.println("📊 ENDPOINT: /api/graficas/asistencia-por-area");
            System.out.println("   Parámetros: mes=" + mes + ", año=" + anio);
            
            // Validar mes
            if (mes < 1 || mes > 12) {
                return ResponseEntity.badRequest().body(
                    crearRespuestaError("El mes debe estar entre 1 y 12")
                );
            }
            
            // Validar año
            if (anio < 2020 || anio > 2100) {
                return ResponseEntity.badRequest().body(
                    crearRespuestaError("El año debe estar entre 2020 y 2100")
                );
            }
            
            List<AsistenciaPorAreaDTO> datos = graficasService.obtenerAsistenciaPorArea(mes, anio);
            
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("titulo", "Distribución de Asistencia por Área (Mes " + mes + "/" + anio + ")");
            respuesta.put("tipo", "PIE_CHART");
            respuesta.put("periodo", "MENSUAL");
            respuesta.put("mes", mes);
            respuesta.put("anio", anio);
            respuesta.put("datos", datos);
            respuesta.put("metadata", crearMetadataAsistenciaPorArea(datos));
            
            System.out.println("✅ Respuesta generada con " + datos.size() + " áreas");
            return ResponseEntity.ok(respuesta);
            
        } catch (Exception e) {
            System.err.println("❌ Error al obtener asistencia por área: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(crearRespuestaError("Error al procesar la solicitud: " + e.getMessage()));
        }
    }

    /**
     * ENDPOINT 3B: Asistencia por áreas con rango de fechas (Pie Chart)
     * GET /api/graficas/asistencia-por-area-rango?fechaInicio=2026-01-01&fechaFin=2026-01-31
     */
    @GetMapping("/asistencia-por-area-rango")
    public ResponseEntity<?> obtenerAsistenciaPorAreaRango(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        
        try {
            System.out.println("📊 ENDPOINT: /api/graficas/asistencia-por-area-rango");
            System.out.println("   Parámetros: fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin);
            
            // Validar fechas
            if (fechaInicio.isAfter(fechaFin)) {
                return ResponseEntity.badRequest().body(
                    crearRespuestaError("La fecha de inicio no puede ser posterior a la fecha de fin")
                );
            }
            
            List<AsistenciaPorAreaDTO> datos = graficasService.obtenerAsistenciaPorAreaRango(fechaInicio, fechaFin);
            
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("titulo", "Distribución de Asistencia por Área (" + fechaInicio + " a " + fechaFin + ")");
            respuesta.put("tipo", "PIE_CHART");
            respuesta.put("periodo", "RANGO");
            respuesta.put("fechaInicio", fechaInicio.toString());
            respuesta.put("fechaFin", fechaFin.toString());
            respuesta.put("datos", datos);
            respuesta.put("metadata", crearMetadataAsistenciaPorArea(datos));
            
            System.out.println("✅ Respuesta generada con " + datos.size() + " áreas");
            return ResponseEntity.ok(respuesta);
            
        } catch (Exception e) {
            System.err.println("❌ Error al obtener asistencia por área (rango): " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(crearRespuestaError("Error al procesar la solicitud: " + e.getMessage()));
        }
    }

    // ========== MÉTODOS AUXILIARES PARA METADATA ==========

    private Map<String, Object> crearMetadataRegistrosPorDia(List<RegistrosPorDiaDTO> datos) {
        Map<String, Object> metadata = new HashMap<>();
        
        int totalRegistros = datos.stream().mapToInt(RegistrosPorDiaDTO::getTotalRegistros).sum();
        int totalEntradas = datos.stream().mapToInt(RegistrosPorDiaDTO::getEntradas).sum();
        int totalSalidas = datos.stream().mapToInt(RegistrosPorDiaDTO::getSalidas).sum();
        int totalPuntuales = datos.stream().mapToInt(RegistrosPorDiaDTO::getPuntuales).sum();
        int totalRetardos = datos.stream().mapToInt(RegistrosPorDiaDTO::getRetardos).sum();
        
        metadata.put("totalRegistros", totalRegistros);
        metadata.put("totalEntradas", totalEntradas);
        metadata.put("totalSalidas", totalSalidas);
        metadata.put("totalPuntuales", totalPuntuales);
        metadata.put("totalRetardos", totalRetardos);
        metadata.put("promedioDiario", totalRegistros > 0 ? (double) totalRegistros / datos.size() : 0.0);
        metadata.put("tasaPuntualidad", totalRegistros > 0 ? (double) totalPuntuales * 100.0 / totalRegistros : 0.0);
        metadata.put("diasAnalizados", datos.size());
        
        return metadata;
    }

    private Map<String, Object> crearMetadataEntradasSalidasSemana(List<EntradasSalidasSemanaDTO> datos) {
        Map<String, Object> metadata = new HashMap<>();
        
        int totalEntradas = datos.stream().mapToInt(EntradasSalidasSemanaDTO::getEntradas).sum();
        int totalSalidas = datos.stream().mapToInt(EntradasSalidasSemanaDTO::getSalidas).sum();
        int totalUsuariosActivos = datos.stream().mapToInt(EntradasSalidasSemanaDTO::getUsuariosActivos).sum();
        
        metadata.put("totalEntradas", totalEntradas);
        metadata.put("totalSalidas", totalSalidas);
        metadata.put("totalUsuariosActivos", totalUsuariosActivos);
        metadata.put("ratioPromedioEntradaSalida", totalSalidas > 0 ? (double) totalEntradas / totalSalidas : 0.0);
        metadata.put("promedioEntradasPorSemana", datos.size() > 0 ? (double) totalEntradas / datos.size() : 0.0);
        metadata.put("semanasAnalizadas", datos.size());
        
        return metadata;
    }

    private Map<String, Object> crearMetadataAsistenciaPorArea(List<AsistenciaPorAreaDTO> datos) {
        Map<String, Object> metadata = new HashMap<>();
        
        int totalAsistencias = datos.stream().mapToInt(AsistenciaPorAreaDTO::getTotalAsistencias).sum();
        int totalUsuariosUnicos = datos.stream().mapToInt(AsistenciaPorAreaDTO::getUsuariosUnicos).sum();
        int totalPuntuales = datos.stream().mapToInt(AsistenciaPorAreaDTO::getAsistenciasPuntuales).sum();
        
        metadata.put("totalAsistencias", totalAsistencias);
        metadata.put("totalUsuariosUnicos", totalUsuariosUnicos);
        metadata.put("totalPuntuales", totalPuntuales);
        metadata.put("tasaPuntualidadGeneral", totalAsistencias > 0 ? (double) totalPuntuales * 100.0 / totalAsistencias : 0.0);
        metadata.put("areasAnalizadas", datos.size());
        
        // Área con más actividad
        if (!datos.isEmpty()) {
            AsistenciaPorAreaDTO areaMasActiva = datos.get(0); // Ya está ordenado descendente
            metadata.put("areaMasActiva", areaMasActiva.getNombreArea());
            metadata.put("asistenciasAreaMasActiva", areaMasActiva.getTotalAsistencias());
        }
        
        return metadata;
    }

    private Map<String, String> crearRespuestaError(String mensaje) {
        Map<String, String> error = new HashMap<>();
        error.put("error", mensaje);
        error.put("timestamp", LocalDate.now().toString());
        return error;
    }
}
