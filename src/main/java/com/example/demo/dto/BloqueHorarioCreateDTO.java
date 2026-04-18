package com.example.demo.dto;

/**
 * DTO para crear un Bloque de Horario
 * Actualizado para usar idDiaHorario en lugar de idHorario
 */
public class BloqueHorarioCreateDTO {
    
    private Integer idDiaHorario;
    private Integer idArea;
    private String nombreBloque;
    private String horaInicio;  // Formato "HH:mm:ss"
    private String horaFin;     // Formato "HH:mm:ss"

    public BloqueHorarioCreateDTO() {
    }

    public BloqueHorarioCreateDTO(Integer idDiaHorario, Integer idArea, String nombreBloque, 
                                  String horaInicio, String horaFin) {
        this.idDiaHorario = idDiaHorario;
        this.idArea = idArea;
        this.nombreBloque = nombreBloque;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    // Getters y Setters
    public Integer getIdDiaHorario() {
        return idDiaHorario;
    }

    public void setIdDiaHorario(Integer idDiaHorario) {
        this.idDiaHorario = idDiaHorario;
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

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }
}
