package com.example.demo.dto;

/**
 * DTO de respuesta después de crear/actualizar un horario semanal
 * Representa SOLO información del HORARIO creado (sin calendario)
 */
public class HorarioSemanalResponseDTO {
    
    private Integer idHorario;
    private String nombreHorario;
    private String descripcion;
    private String activoHorario;
    private Integer diasCreados; // Cantidad de días vinculados (siempre 7)
    private Integer totalBloques; // Total de bloques asignados a los 7 días

    // Constructores
    public HorarioSemanalResponseDTO() {
    }

    public HorarioSemanalResponseDTO(Integer idHorario, String nombreHorario, 
                                     String descripcion, String activoHorario, 
                                     Integer diasCreados, Integer totalBloques) {
        this.idHorario = idHorario;
        this.nombreHorario = nombreHorario;
        this.descripcion = descripcion;
        this.activoHorario = activoHorario;
        this.diasCreados = diasCreados;
        this.totalBloques = totalBloques;
    }

    // Getters y Setters
    public Integer getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Integer idHorario) {
        this.idHorario = idHorario;
    }

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

    public Integer getDiasCreados() {
        return diasCreados;
    }

    public void setDiasCreados(Integer diasCreados) {
        this.diasCreados = diasCreados;
    }

    public Integer getTotalBloques() {
        return totalBloques;
    }

    public void setTotalBloques(Integer totalBloques) {
        this.totalBloques = totalBloques;
    }
}
