package com.example.demo.dto;

/**
 * DTO básico para HORARIO
 * Representa SOLO los campos de la tabla HORARIO (sin días ni bloques)
 */
public class HorarioDTO {
    private Integer idHorario;
    private String nombreHorario;
    private String descripcion;
    private String activoHorario;

    // Constructor sin argumentos
    public HorarioDTO() {
    }

    // Constructor con todos los campos de HORARIO
    public HorarioDTO(Integer idHorario, String nombreHorario, 
                      String descripcion, String activoHorario) {
        this.idHorario = idHorario;
        this.nombreHorario = nombreHorario;
        this.descripcion = descripcion;
        this.activoHorario = activoHorario;
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
}
