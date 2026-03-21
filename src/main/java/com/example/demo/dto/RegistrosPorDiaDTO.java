package com.example.demo.dto;

import java.time.LocalDate;

public class RegistrosPorDiaDTO {
    private LocalDate fecha;
    private String diaSemana; // "Lunes", "Martes", etc.
    private Integer totalRegistros;
    private Integer entradas;
    private Integer salidas;
    private Integer puntuales;
    private Integer retardos;
    private Integer anticipados;

    public RegistrosPorDiaDTO() {
    }

    public RegistrosPorDiaDTO(LocalDate fecha, String diaSemana, Integer totalRegistros, 
                              Integer entradas, Integer salidas, Integer puntuales, 
                              Integer retardos, Integer anticipados) {
        this.fecha = fecha;
        this.diaSemana = diaSemana;
        this.totalRegistros = totalRegistros;
        this.entradas = entradas;
        this.salidas = salidas;
        this.puntuales = puntuales;
        this.retardos = retardos;
        this.anticipados = anticipados;
    }

    // Getters y Setters
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Integer getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(Integer totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    public Integer getEntradas() {
        return entradas;
    }

    public void setEntradas(Integer entradas) {
        this.entradas = entradas;
    }

    public Integer getSalidas() {
        return salidas;
    }

    public void setSalidas(Integer salidas) {
        this.salidas = salidas;
    }

    public Integer getPuntuales() {
        return puntuales;
    }

    public void setPuntuales(Integer puntuales) {
        this.puntuales = puntuales;
    }

    public Integer getRetardos() {
        return retardos;
    }

    public void setRetardos(Integer retardos) {
        this.retardos = retardos;
    }

    public Integer getAnticipados() {
        return anticipados;
    }

    public void setAnticipados(Integer anticipados) {
        this.anticipados = anticipados;
    }
}
