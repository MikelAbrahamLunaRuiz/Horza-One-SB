package com.example.demo.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AsistenciaPorAreaDTO;
import com.example.demo.dto.EntradasSalidasSemanaDTO;
import com.example.demo.dto.RegistrosPorDiaDTO;
import com.example.demo.model.Area;
import com.example.demo.model.Registro;
import com.example.demo.respository.AreaRepository;
import com.example.demo.respository.RegistroRepository;
import com.example.demo.service.GraficasService;

@Service
public class GraficasServiceImpl implements GraficasService {

    @Autowired
    private RegistroRepository registroRepository;
    
    @Autowired
    private AreaRepository areaRepository;

    // Colores predefinidos para áreas (HEX)
    private static final String[] COLORES_AREAS = {
        "#4CAF50", // Verde
        "#2196F3", // Azul
        "#FFC107", // Amarillo
        "#9C27B0", // Morado
        "#FF5722", // Naranja
        "#00BCD4", // Cyan
        "#E91E63", // Rosa
        "#607D8B"  // Gris Azulado
    };

    @Override
    public List<RegistrosPorDiaDTO> obtenerRegistrosPorDia(LocalDate fechaInicio, LocalDate fechaFin) {
        System.out.println("📊 GRÁFICA 1: Obteniendo registros por día del " + fechaInicio + " al " + fechaFin);
        
        // Obtener todos los registros del periodo
        List<Registro> registros = registroRepository.findByFechaBetween(fechaInicio, fechaFin);
        
        // Agrupar por fecha
        Map<LocalDate, List<Registro>> registrosPorFecha = registros.stream()
                .collect(Collectors.groupingBy(Registro::getFecha));
        
        // Crear lista de resultados para TODOS los días del rango (incluyendo días sin registros)
        List<RegistrosPorDiaDTO> resultado = new ArrayList<>();
        
        LocalDate fechaActual = fechaInicio;
        while (!fechaActual.isAfter(fechaFin)) {
            List<Registro> registrosDia = registrosPorFecha.getOrDefault(fechaActual, Collections.emptyList());
            
            // Obtener nombre del día de la semana en español
            String diaSemana = obtenerNombreDiaSemana(fechaActual);
            
            // Contar por tipo y estado
            int totalRegistros = registrosDia.size();
            int entradas = (int) registrosDia.stream().filter(r -> "Entrada".equals(r.getTipoRegistro())).count();
            int salidas = (int) registrosDia.stream().filter(r -> "Salida".equals(r.getTipoRegistro())).count();
            int puntuales = (int) registrosDia.stream().filter(r -> "Puntual".equals(r.getEstadoRegistro())).count();
            int retardos = (int) registrosDia.stream().filter(r -> "Retardo".equals(r.getEstadoRegistro())).count();
            int anticipados = (int) registrosDia.stream().filter(r -> "Anticipado".equals(r.getEstadoRegistro())).count();
            
            RegistrosPorDiaDTO dto = new RegistrosPorDiaDTO(
                    fechaActual,
                    diaSemana,
                    totalRegistros,
                    entradas,
                    salidas,
                    puntuales,
                    retardos,
                    anticipados
            );
            
            resultado.add(dto);
            fechaActual = fechaActual.plusDays(1);
        }
        
        System.out.println("✅ Total de días procesados: " + resultado.size());
        return resultado;
    }

    @Override
    public List<EntradasSalidasSemanaDTO> obtenerEntradasSalidasPorSemana(LocalDate fechaInicio, LocalDate fechaFin) {
        System.out.println("📊 GRÁFICA 2: Obteniendo entradas/salidas por semana del " + fechaInicio + " al " + fechaFin);
        
        List<Registro> registros = registroRepository.findByFechaBetween(fechaInicio, fechaFin);
        
        // Agrupar por año y semana
        Map<String, List<Registro>> registrosPorSemana = registros.stream()
                .collect(Collectors.groupingBy(r -> 
                    r.getFecha().get(ChronoField.ALIGNED_WEEK_OF_YEAR) + "-" + r.getFecha().getYear()
                ));
        
        List<EntradasSalidasSemanaDTO> resultado = new ArrayList<>();
        
        // Iterar por cada semana en el rango
        LocalDate fechaActual = fechaInicio.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        
        while (!fechaActual.isAfter(fechaFin)) {
            int numeroSemana = fechaActual.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
            int anio = fechaActual.getYear();
            
            LocalDate inicioSemana = fechaActual;
            LocalDate finSemana = fechaActual.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
            
            // Filtrar registros de esta semana
            List<Registro> registrosSemana = registros.stream()
                    .filter(r -> !r.getFecha().isBefore(inicioSemana) && !r.getFecha().isAfter(finSemana))
                    .collect(Collectors.toList());
            
            int entradas = (int) registrosSemana.stream().filter(r -> "Entrada".equals(r.getTipoRegistro())).count();
            int salidas = (int) registrosSemana.stream().filter(r -> "Salida".equals(r.getTipoRegistro())).count();
            int usuariosActivos = (int) registrosSemana.stream()
                    .map(r -> r.getUsuario().getMatricula())
                    .distinct()
                    .count();
            
            EntradasSalidasSemanaDTO dto = new EntradasSalidasSemanaDTO(
                    anio,
                    numeroSemana,
                    inicioSemana,
                    finSemana,
                    entradas,
                    salidas,
                    usuariosActivos
            );
            
            resultado.add(dto);
            
            // Avanzar a la siguiente semana
            fechaActual = fechaActual.plusWeeks(1);
        }
        
        System.out.println("✅ Total de semanas procesadas: " + resultado.size());
        return resultado;
    }

    @Override
    public List<AsistenciaPorAreaDTO> obtenerAsistenciaPorArea(Integer mes, Integer anio) {
        System.out.println("📊 GRÁFICA 3: Obteniendo asistencia por área - Mes: " + mes + ", Año: " + anio);
        
        // Calcular primer y último día del mes
        LocalDate primerDiaMes = LocalDate.of(anio, mes, 1);
        LocalDate ultimoDiaMes = primerDiaMes.with(TemporalAdjusters.lastDayOfMonth());
        
        List<Registro> registros = registroRepository.findByFechaBetween(primerDiaMes, ultimoDiaMes);
        
        // Agrupar por área
        Map<Integer, List<Registro>> registrosPorArea = registros.stream()
                .collect(Collectors.groupingBy(r -> r.getArea().getIdArea()));
        
        List<AsistenciaPorAreaDTO> resultado = new ArrayList<>();
        int totalGeneralAsistencias = registros.size();
        
        int colorIndex = 0;
        
        for (Map.Entry<Integer, List<Registro>> entry : registrosPorArea.entrySet()) {
            Integer idArea = entry.getKey();
            List<Registro> registrosArea = entry.getValue();
            
            // Obtener nombre del área (del primer registro)
            String nombreArea = registrosArea.isEmpty() ? "Área " + idArea : 
                                registrosArea.get(0).getArea().getNombreArea();
            
            int usuariosUnicos = (int) registrosArea.stream()
                    .map(r -> r.getUsuario().getMatricula())
                    .distinct()
                    .count();
            
            int puntuales = (int) registrosArea.stream()
                    .filter(r -> "Puntual".equals(r.getEstadoRegistro()))
                    .count();
            
            int retardos = (int) registrosArea.stream()
                    .filter(r -> "Retardo".equals(r.getEstadoRegistro()))
                    .count();
            
            int anticipados = (int) registrosArea.stream()
                    .filter(r -> "Anticipado".equals(r.getEstadoRegistro()))
                    .count();
            
            int totalAsistencias = registrosArea.size();
            
            AsistenciaPorAreaDTO dto = new AsistenciaPorAreaDTO(
                    idArea,
                    nombreArea,
                    usuariosUnicos,
                    puntuales,
                    retardos,
                    anticipados,
                    totalAsistencias
            );
            
            // Calcular porcentaje del total
            if (totalGeneralAsistencias > 0) {
                dto.setPorcentajeDelTotal((double) totalAsistencias * 100.0 / totalGeneralAsistencias);
            }
            
            // Asignar color
            dto.setColor(COLORES_AREAS[colorIndex % COLORES_AREAS.length]);
            colorIndex++;
            
            resultado.add(dto);
        }
        
        // Ordenar por total de asistencias (descendente)
        resultado.sort((a, b) -> b.getTotalAsistencias().compareTo(a.getTotalAsistencias()));
        
        System.out.println("✅ Total de áreas procesadas: " + resultado.size());
        return resultado;
    }

    @Override
    public List<AsistenciaPorAreaDTO> obtenerAsistenciaPorAreaRango(LocalDate fechaInicio, LocalDate fechaFin) {
        System.out.println("🔍 Obteniendo asistencia por área - Rango: " + fechaInicio + " a " + fechaFin);
        
        List<AsistenciaPorAreaDTO> resultado = new ArrayList<>();
        
        // 1. Obtener todas las áreas activas
        List<Area> areas = areaRepository.findByActivoArea("Activo");
        System.out.println("📋 Áreas activas encontradas: " + areas.size());
        
        // 2. Obtener todos los registros de entrada en el rango
        List<Registro> registros = registroRepository.findByFechaBetween(fechaInicio, fechaFin).stream()
            .filter(r -> r.getTipoRegistro() != null && "Entrada".equalsIgnoreCase(r.getTipoRegistro()))
            .collect(Collectors.toList());
        
        System.out.println("📊 Total de registros de entrada en rango: " + registros.size());
        
        // 3. Calcular total general para porcentajes
        int totalGeneralAsistencias = registros.size();
        
        // 4. Procesar cada área
        int colorIndex = 0;
        for (Area area : areas) {
            // Filtrar registros del área
            List<Registro> registrosArea = registros.stream()
                .filter(r -> r.getDispositivo() != null && 
                           r.getDispositivo().getArea() != null &&
                           r.getDispositivo().getArea().getIdArea().equals(area.getIdArea()))
                .collect(Collectors.toList());
            
            int totalAsistencias = registrosArea.size();
            
            // Contar usuarios únicos
            long usuariosUnicos = registrosArea.stream()
                .map(r -> r.getUsuario().getMatricula())
                .distinct()
                .count();
            
            // Contar por estado
            long asistenciasPuntuales = registrosArea.stream()
                .filter(r -> "Puntual".equalsIgnoreCase(r.getEstadoRegistro()))
                .count();
            
            long retardos = registrosArea.stream()
                .filter(r -> "Retardo".equalsIgnoreCase(r.getEstadoRegistro()))
                .count();
            
            long anticipados = registrosArea.stream()
                .filter(r -> "Anticipado".equalsIgnoreCase(r.getEstadoRegistro()))
                .count();
            
            // Crear DTO
            AsistenciaPorAreaDTO dto = new AsistenciaPorAreaDTO(
                    area.getIdArea(),
                    area.getNombreArea(),
                    (int) usuariosUnicos,
                    (int) asistenciasPuntuales,
                    (int) retardos,
                    (int) anticipados,
                    totalAsistencias
            );
            
            // Calcular porcentaje del total
            if (totalGeneralAsistencias > 0) {
                dto.setPorcentajeDelTotal((double) totalAsistencias * 100.0 / totalGeneralAsistencias);
            }
            
            // Asignar color
            dto.setColor(COLORES_AREAS[colorIndex % COLORES_AREAS.length]);
            colorIndex++;
            
            resultado.add(dto);
        }
        
        // Ordenar por total de asistencias (descendente)
        resultado.sort((a, b) -> b.getTotalAsistencias().compareTo(a.getTotalAsistencias()));
        
        System.out.println("✅ Total de áreas procesadas (rango): " + resultado.size());
        return resultado;
    }

    /**
     * Método auxiliar para obtener el nombre del día de la semana en español
     */
    private String obtenerNombreDiaSemana(LocalDate fecha) {
        DayOfWeek dia = fecha.getDayOfWeek();
        
        switch (dia) {
            case MONDAY: return "Lunes";
            case TUESDAY: return "Martes";
            case WEDNESDAY: return "Miércoles";
            case THURSDAY: return "Jueves";
            case FRIDAY: return "Viernes";
            case SATURDAY: return "Sábado";
            case SUNDAY: return "Domingo";
            default: return dia.getDisplayName(TextStyle.FULL, Locale.getDefault());
        }
    }
}
