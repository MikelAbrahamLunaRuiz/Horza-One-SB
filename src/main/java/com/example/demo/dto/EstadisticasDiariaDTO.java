package com.example.demo.dto;

import java.time.LocalDate;

public class EstadisticasDiariaDTO {
    private LocalDate fecha;
    private Integer totalTrabajadores;
    private Integer totalEntradas;
    private Integer totalSalidas;
    private Integer entradasPuntuales;
    private Integer retardos;
    private Integer inasistencias;
    private Integer permisos;
    private Double porcentajePuntuales;
    private Double porcentajeRetardos;
    private Double porcentajeInasistencias;
    private Double porcentajePermisos;

    public EstadisticasDiariaDTO() {
    }

    public EstadisticasDiariaDTO(LocalDate fecha, Integer totalTrabajadores, Integer totalEntradas,
                                 Integer totalSalidas, Integer entradasPuntuales, Integer retardos,
                                 Integer inasistencias, Integer permisos) {
        this.fecha = fecha;
        this.totalTrabajadores = totalTrabajadores;
        this.totalEntradas = totalEntradas;
        this.totalSalidas = totalSalidas;
        this.entradasPuntuales = entradasPuntuales;
        this.retardos = retardos;
        this.inasistencias = inasistencias;
        this.permisos = permisos;

        // Calcular porcentajes
        if (totalTrabajadores > 0) {
            this.porcentajePuntuales = Math.round((entradasPuntuales * 100.0 / totalTrabajadores) * 100.0) / 100.0;
            this.porcentajeRetardos = Math.round((retardos * 100.0 / totalTrabajadores) * 100.0) / 100.0;
            this.porcentajeInasistencias = Math.round((inasistencias * 100.0 / totalTrabajadores) * 100.0) / 100.0;
            this.porcentajePermisos = Math.round((permisos * 100.0 / totalTrabajadores) * 100.0) / 100.0;
        } else {
            this.porcentajePuntuales = 0.0;
            this.porcentajeRetardos = 0.0;
            this.porcentajeInasistencias = 0.0;
            this.porcentajePermisos = 0.0;
        }
    }

    // Getters y Setters
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Integer getTotalTrabajadores() { return totalTrabajadores; }
    public void setTotalTrabajadores(Integer totalTrabajadores) { this.totalTrabajadores = totalTrabajadores; }

    public Integer getTotalEntradas() { return totalEntradas; }
    public void setTotalEntradas(Integer totalEntradas) { this.totalEntradas = totalEntradas; }

    public Integer getTotalSalidas() { return totalSalidas; }
    public void setTotalSalidas(Integer totalSalidas) { this.totalSalidas = totalSalidas; }

    public Integer getEntradasPuntuales() { return entradasPuntuales; }
    public void setEntradasPuntuales(Integer entradasPuntuales) { this.entradasPuntuales = entradasPuntuales; }

    public Integer getRetardos() { return retardos; }
    public void setRetardos(Integer retardos) { this.retardos = retardos; }

    public Integer getInasistencias() { return inasistencias; }
    public void setInasistencias(Integer inasistencias) { this.inasistencias = inasistencias; }

    public Integer getPermisos() { return permisos; }
    public void setPermisos(Integer permisos) { this.permisos = permisos; }

    public Double getPorcentajePuntuales() { return porcentajePuntuales; }
    public void setPorcentajePuntuales(Double porcentajePuntuales) { this.porcentajePuntuales = porcentajePuntuales; }

    public Double getPorcentajeRetardos() { return porcentajeRetardos; }
    public void setPorcentajeRetardos(Double porcentajeRetardos) { this.porcentajeRetardos = porcentajeRetardos; }

    public Double getPorcentajeInasistencias() { return porcentajeInasistencias; }
    public void setPorcentajeInasistencias(Double porcentajeInasistencias) { this.porcentajeInasistencias = porcentajeInasistencias; }

    public Double getPorcentajePermisos() { return porcentajePermisos; }
    public void setPorcentajePermisos(Double porcentajePermisos) { this.porcentajePermisos = porcentajePermisos; }
}