package com.example.demo.dto;

public class AreaDTO {
    private Integer idArea;
    private String nombreArea;
    private String descripcionArea;
    private String activoArea;
    private String ubicacion;

    public AreaDTO() {
    }

    public AreaDTO(Integer idArea, String nombreArea, String descripcionArea, String activoArea, String ubicacion) {
        this.idArea = idArea;
        this.nombreArea = nombreArea;
        this.descripcionArea = descripcionArea;
        this.activoArea = activoArea;
        this.ubicacion = ubicacion;
    }

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

    public String getDescripcionArea() {
        return descripcionArea;
    }

    public void setDescripcionArea(String descripcionArea) {
        this.descripcionArea = descripcionArea;
    }

    public String getActivoArea() {
        return activoArea;
    }

    public void setActivoArea(String activoArea) {
        this.activoArea = activoArea;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
