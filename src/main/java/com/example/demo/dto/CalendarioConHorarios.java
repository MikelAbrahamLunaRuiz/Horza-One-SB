package com.example.demo.dto;

import java.time.LocalDate;
import java.util.List;

public class CalendarioConHorarios {
    private Integer idCalendario;
    private String nombreCalendario;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String descripcion;
    private String activoCalendario;
    private List<HorarioConBloques> horarios;

    public CalendarioConHorarios() {
    }

    public CalendarioConHorarios(Integer idCalendario, String nombreCalendario, LocalDate fechaInicio, 
                                 LocalDate fechaFin, String descripcion, String activoCalendario, 
                                 List<HorarioConBloques> horarios) {
        this.idCalendario = idCalendario;
        this.nombreCalendario = nombreCalendario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripcion = descripcion;
        this.activoCalendario = activoCalendario;
        this.horarios = horarios;
    }

    public Integer getIdCalendario() {
        return idCalendario;
    }

    public void setIdCalendario(Integer idCalendario) {
        this.idCalendario = idCalendario;
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

    public List<HorarioConBloques> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<HorarioConBloques> horarios) {
        this.horarios = horarios;
    }
}
