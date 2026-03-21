package com.example.demo.dto;

import java.time.LocalDate;

public class CalendarioDTO {
    private Integer idCalendario;
    private Integer idHorario; // FK al horario asignado (puede ser null)
    private String nombreCalendario;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String descripcion;
    private String activoCalendario;

    // Constructor sin argumentos
    public CalendarioDTO() {
    }

    // Constructor con todos los argumentos
    public CalendarioDTO(Integer idCalendario, Integer idHorario, String nombreCalendario, 
                         LocalDate fechaInicio, LocalDate fechaFin, String descripcion, String activoCalendario) {
        this.idCalendario = idCalendario;
        this.idHorario = idHorario;
        this.nombreCalendario = nombreCalendario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripcion = descripcion;
        this.activoCalendario = activoCalendario;
    }

    // Getters y Setters
    public Integer getIdCalendario() {
        return idCalendario;
    }

    public void setIdCalendario(Integer idCalendario) {
        this.idCalendario = idCalendario;
    }

    public Integer getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Integer idHorario) {
        this.idHorario = idHorario;
    }

    public String getNombreCalendario() {
        return nombreCalendario;
    }

    public void setNombreCalendario(String nombreCalendario) {
        this.nombreCalendario = nombreCalendario;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
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
