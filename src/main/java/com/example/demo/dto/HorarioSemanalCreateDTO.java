package com.example.demo.dto;

import java.util.List;
import java.util.Map;

/**
 * DTO para crear un horario semanal completo
 * Contiene información para los 7 días de la semana
 */
public class HorarioSemanalCreateDTO {
    
    private String nombreHorario;
    private String descripcion;
    private String activoHorario; // "Activo" o "Inactivo"
    
    /**
     * Mapa con los bloques asignados a cada día
     * Key: día de la semana ("Lunes", "Martes", etc.)
     * Value: Lista de IDs de bloques asignados a ese día
     * 
     * Ejemplo:
     * {
     *   "Lunes": [1, 2, 3],
     *   "Martes": [2, 4],
     *   "Miércoles": []
     * }
     */
    private Map<String, List<Integer>> bloquesPorDia;

    // Constructores
    public HorarioSemanalCreateDTO() {
    }

    public HorarioSemanalCreateDTO(String nombreHorario, String descripcion, String activoHorario, 
                                   Map<String, List<Integer>> bloquesPorDia) {
        this.nombreHorario = nombreHorario;
        this.descripcion = descripcion;
        this.activoHorario = activoHorario;
        this.bloquesPorDia = bloquesPorDia;
    }

    // Getters y Setters
    public String getNombreHorario() {
        return nombreHorario;
    }

    public void setNombreHorario(String nombreHorario) {
        this.nombreHorario = nombreHorario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getActivoHorario() {
        return activoHorario;
    }

    public void setActivoHorario(String activoHorario) {
        this.activoHorario = activoHorario;
    }

    public Map<String, List<Integer>> getBloquesPorDia() {
        return bloquesPorDia;
    }

    public void setBloquesPorDia(Map<String, List<Integer>> bloquesPorDia) {
        this.bloquesPorDia = bloquesPorDia;
    }
}
