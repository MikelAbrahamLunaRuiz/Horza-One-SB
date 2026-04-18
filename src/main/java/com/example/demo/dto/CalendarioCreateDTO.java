package com.example.demo.dto;

/**
 * DTO para crear un Calendario
 */
public class CalendarioCreateDTO {
    
    private Integer idHorario;     // FK al horario que se usará en este calendario
    private String nombreCalendario;
    private String fechaInicio;    // Formato "yyyy-MM-dd"
    private String fechaFin;       // Formato "yyyy-MM-dd"
    private String descripcion;
    private String activoCalendario = "Activo";  // Por defecto Activo

    public CalendarioCreateDTO() {
    }

    public CalendarioCreateDTO(Integer idHorario, String nombreCalendario, String fechaInicio, String fechaFin, 
                              String descripcion, String activoCalendario) {
        this.idHorario = idHorario;
        this.nombreCalendario = nombreCalendario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripcion = descripcion;
        this.activoCalendario = activoCalendario;
    }

    public Integer getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Integer idHorario) {
        this.idHorario = idHorario;
    }

    // Getters y Setters
    public String getNombreCalendario() {
        return nombreCalendario;
    }

    public void setNombreCalendario(String nombreCalendario) {
        this.nombreCalendario = nombreCalendario;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getActivoCalendario() {
        return activoCalendario;
    }

    public void setActivoCalendario(String activoCalendario) {
        this.activoCalendario = activoCalendario;
    }
}
