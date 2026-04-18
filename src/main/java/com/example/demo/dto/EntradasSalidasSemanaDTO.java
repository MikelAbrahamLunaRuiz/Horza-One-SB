package com.example.demo.dto;

import java.time.LocalDate;

public class EntradasSalidasSemanaDTO {
    private Integer anio;
    private Integer numeroSemana;
    private LocalDate inicioSemana;
    private LocalDate finSemana;
    private Integer entradas;
    private Integer salidas;
    private Integer usuariosActivos;
    private Double ratioEntradaSalida; // entradas / salidas

    public EntradasSalidasSemanaDTO() {
    }

    public EntradasSalidasSemanaDTO(Integer anio, Integer numeroSemana, LocalDate inicioSemana, 
                                    LocalDate finSemana, Integer entradas, Integer salidas, 
                                    Integer usuariosActivos) {
        this.anio = anio;
        this.numeroSemana = numeroSemana;
        this.inicioSemana = inicioSemana;
        this.finSemana = finSemana;
        this.entradas = entradas;
        this.salidas = salidas;
        this.usuariosActivos = usuariosActivos;
        this.ratioEntradaSalida = salidas > 0 ? (double) entradas / salidas : 0.0;
    }

    // Getters y Setters
    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getNumeroSemana() {
        return numeroSemana;
    }

    public void setNumeroSemana(Integer numeroSemana) {
        this.numeroSemana = numeroSemana;
    }

    public LocalDate getInicioSemana() {
        return inicioSemana;
    }

    public void setInicioSemana(LocalDate inicioSemana) {
        this.inicioSemana = inicioSemana;
    }

    public LocalDate getFinSemana() {
        return finSemana;
    }

    public void setFinSemana(LocalDate finSemana) {
        this.finSemana = finSemana;
    }

    public Integer getEntradas() {
        return entradas;
    }

    public void setEntradas(Integer entradas) {
        this.entradas = entradas;
        calcularRatio();
    }

    public Integer getSalidas() {
        return salidas;
    }

    public void setSalidas(Integer salidas) {
        this.salidas = salidas;
        calcularRatio();
    }

    public Integer getUsuariosActivos() {
        return usuariosActivos;
    }

    public void setUsuariosActivos(Integer usuariosActivos) {
        this.usuariosActivos = usuariosActivos;
    }

    public Double getRatioEntradaSalida() {
        return ratioEntradaSalida;
    }

    public void setRatioEntradaSalida(Double ratioEntradaSalida) {
        this.ratioEntradaSalida = ratioEntradaSalida;
    }

    private void calcularRatio() {
        if (entradas != null && salidas != null && salidas > 0) {
            this.ratioEntradaSalida = (double) entradas / salidas;
        } else {
            this.ratioEntradaSalida = 0.0;
        }
    }
}
