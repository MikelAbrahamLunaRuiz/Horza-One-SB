package com.example.demo.dto;

import java.time.LocalDate;

public class ConsultaRegistrosRequest {
    private Integer matricula;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String tipoRegistro; // "Entrada", "Salida", o null para ambos

    public ConsultaRegistrosRequest() {
    }

    public ConsultaRegistrosRequest(Integer matricula, LocalDate fechaInicio, LocalDate fechaFin, String tipoRegistro) {
        this.matricula = matricula;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tipoRegistro = tipoRegistro;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
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

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }
}
