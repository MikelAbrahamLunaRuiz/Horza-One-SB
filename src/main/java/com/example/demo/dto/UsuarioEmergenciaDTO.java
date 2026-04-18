package com.example.demo.dto;

public class UsuarioEmergenciaDTO {
    
    private Integer matricula;
    private String nombreCompleto;
    private String nombreArea;
    private String horaIngreso;
    private String nombreDispositivo;
    private String fechaIngreso;
    
    public UsuarioEmergenciaDTO() {
    }
    
    public UsuarioEmergenciaDTO(Integer matricula, String nombreCompleto, String nombreArea, 
                                String horaIngreso, String nombreDispositivo, String fechaIngreso) {
        this.matricula = matricula;
        this.nombreCompleto = nombreCompleto;
        this.nombreArea = nombreArea;
        this.horaIngreso = horaIngreso;
        this.nombreDispositivo = nombreDispositivo;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }

    public String getHoraIngreso() {
        return horaIngreso;
    }

    public void setHoraIngreso(String horaIngreso) {
        this.horaIngreso = horaIngreso;
    }

    public String getNombreDispositivo() {
        return nombreDispositivo;
    }

    public void setNombreDispositivo(String nombreDispositivo) {
        this.nombreDispositivo = nombreDispositivo;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
}
