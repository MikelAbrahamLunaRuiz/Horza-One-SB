package com.example.demo.dto;

import java.time.LocalDate;

public class PermisoDiaRequest {

    private String tipoPermiso; // "Descanso", "Feriado", "No Laborable"
    private LocalDate fecha;
    private String descripcion;
    private Boolean esGeneral;
    private Integer matricula; // Solo si esGeneral = false

    // Constructor vacío
    public PermisoDiaRequest() {
    }

    // Constructor completo
    public PermisoDiaRequest(String tipoPermiso, LocalDate fecha, String descripcion, Boolean esGeneral, Integer matricula) {
        this.tipoPermiso = tipoPermiso;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.esGeneral = esGeneral;
        this.matricula = matricula;
    }

    // Getters y Setters
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

    // Validación
    public boolean isValid() {
        if (tipoPermiso == null || fecha == null) {
            return false;
        }
        // Si es específico, debe tener matrícula
        if (Boolean.FALSE.equals(esGeneral) && matricula == null) {
            return false;
        }
        // Si es general, NO debe tener matrícula
        if (Boolean.TRUE.equals(esGeneral) && matricula != null) {
            return false;
        }
        return true;
    }
}
