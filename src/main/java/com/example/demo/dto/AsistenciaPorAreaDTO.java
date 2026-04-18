package com.example.demo.dto;

public class AsistenciaPorAreaDTO {
    private Integer idArea;
    private String nombreArea;
    private Integer usuariosUnicos;
    private Integer asistenciasPuntuales;
    private Integer retardos;
    private Integer anticipados;
    private Integer totalAsistencias;
    private Double porcentajePuntualidad;
    private Double porcentajeDelTotal; // % respecto al total general
    private String color; // Color para la gráfica

    public AsistenciaPorAreaDTO() {
    }

    public AsistenciaPorAreaDTO(Integer idArea, String nombreArea, Integer usuariosUnicos, 
                                Integer asistenciasPuntuales, Integer retardos, Integer anticipados,
                                Integer totalAsistencias) {
        this.idArea = idArea;
        this.nombreArea = nombreArea;
        this.usuariosUnicos = usuariosUnicos;
        this.asistenciasPuntuales = asistenciasPuntuales;
        this.retardos = retardos;
        this.anticipados = anticipados;
        this.totalAsistencias = totalAsistencias;
        calcularPorcentajePuntualidad();
    }

    // Getters y Setters
    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }

    public Integer getUsuariosUnicos() {
        return usuariosUnicos;
    }

    public void setUsuariosUnicos(Integer usuariosUnicos) {
        this.usuariosUnicos = usuariosUnicos;
    }

    public Integer getAsistenciasPuntuales() {
        return asistenciasPuntuales;
    }

    public void setAsistenciasPuntuales(Integer asistenciasPuntuales) {
        this.asistenciasPuntuales = asistenciasPuntuales;
        calcularPorcentajePuntualidad();
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

    public Integer getTotalAsistencias() {
        return totalAsistencias;
    }

    public void setTotalAsistencias(Integer totalAsistencias) {
        this.totalAsistencias = totalAsistencias;
        calcularPorcentajePuntualidad();
    }

    public Double getPorcentajePuntualidad() {
        return porcentajePuntualidad;
    }

    public void setPorcentajePuntualidad(Double porcentajePuntualidad) {
        this.porcentajePuntualidad = porcentajePuntualidad;
    }

    public Double getPorcentajeDelTotal() {
        return porcentajeDelTotal;
    }

    public void setPorcentajeDelTotal(Double porcentajeDelTotal) {
        this.porcentajeDelTotal = porcentajeDelTotal;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    private void calcularPorcentajePuntualidad() {
        if (asistenciasPuntuales != null && totalAsistencias != null && totalAsistencias > 0) {
            this.porcentajePuntualidad = (double) asistenciasPuntuales * 100.0 / totalAsistencias;
        } else {
            this.porcentajePuntualidad = 0.0;
        }
    }
}
