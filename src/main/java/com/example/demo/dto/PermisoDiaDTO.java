package com.example.demo.dto;

import java.time.LocalDate;

public class PermisoDiaDTO {

    private Integer idPermiso;
    private String tipoPermiso; // "Descanso", "Feriado", "No Laborable"
    private LocalDate fecha;
    private String descripcion;
    private Boolean esGeneral;
    private Integer matricula;
    private String nombreCompleto; // Nombre del usuario (si es específico)
    private String activo;

    // Constructor vacío
    public PermisoDiaDTO() {
    }

    // Constructor completo
    public PermisoDiaDTO(Integer idPermiso, String tipoPermiso, LocalDate fecha, String descripcion, 
                         Boolean esGeneral, Integer matricula, String nombreCompleto, String activo) {
        this.idPermiso = idPermiso;
        this.tipoPermiso = tipoPermiso;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.esGeneral = esGeneral;
        this.matricula = matricula;
        this.nombreCompleto = nombreCompleto;
        this.activo = activo;
    }

    // Getters y Setters
    public Integer getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(Integer idPermiso) {
        this.idPermiso = idPermiso;
    }

    public String getTipoPermiso() {
        return tipoPermiso;
    }

    public void setTipoPermiso(String tipoPermiso) {
        this.tipoPermiso = tipoPermiso;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getEsGeneral() {
        return esGeneral;
    }

    public void setEsGeneral(Boolean esGeneral) {
        this.esGeneral = esGeneral;
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

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }
}
