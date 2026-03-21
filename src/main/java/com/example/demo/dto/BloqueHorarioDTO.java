package com.example.demo.dto;

import java.time.LocalTime;

public class BloqueHorarioDTO {
    private Integer idBloque;
    private Integer idHorario;
    private Integer idArea;
    private String nombreArea;
    private String nombreBloque;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    // Constructor sin argumentos
    public BloqueHorarioDTO() {
    }

    // Constructor con todos los argumentos
    public BloqueHorarioDTO(Integer idBloque, Integer idHorario, Integer idArea, String nombreArea,
                           String nombreBloque, LocalTime horaInicio, LocalTime horaFin) {
        this.idBloque = idBloque;
        this.idHorario = idHorario;
        this.idArea = idArea;
        this.nombreArea = nombreArea;
        this.nombreBloque = nombreBloque;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    // Getters y Setters
    public Integer getIdBloque() {
        return idBloque;
    }

    public void setIdBloque(Integer idBloque) {
        this.idBloque = idBloque;
    }

    public Integer getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Integer idHorario) {
        this.idHorario = idHorario;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public String getNombreBloque() {
        return nombreBloque;
    }

    public void setNombreBloque(String nombreBloque) {
        this.nombreBloque = nombreBloque;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }
}
