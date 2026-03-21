package com.example.demo.dto;

public class DispositivoDTO {
    private Integer idDispositivo;
    private Integer idArea;
    private String nombreDispositivo;
    private String descripcionDispositivo;
    private String activoDispositivo;
    private String nombreArea; // De la tabla AREA (JOIN)
    private String ubicacionArea; // De la tabla AREA (JOIN)

    // Constructor sin argumentos
    public DispositivoDTO() {
    }

    // Constructor con todos los argumentos
    public DispositivoDTO(Integer idDispositivo, Integer idArea, String nombreDispositivo, String descripcionDispositivo, String activoDispositivo, String nombreArea, String ubicacionArea) {
        this.idDispositivo = idDispositivo;
        this.idArea = idArea;
        this.nombreDispositivo = nombreDispositivo;
        this.descripcionDispositivo = descripcionDispositivo;
        this.activoDispositivo = activoDispositivo;
        this.nombreArea = nombreArea;
        this.ubicacionArea = ubicacionArea;
    }

    // Getters y Setters
    public Integer getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(Integer idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public String getNombreDispositivo() {
        return nombreDispositivo;
    }

    public void setNombreDispositivo(String nombreDispositivo) {
        this.nombreDispositivo = nombreDispositivo;
    }

    public String getDescripcionDispositivo() {
        return descripcionDispositivo;
    }

    public void setDescripcionDispositivo(String descripcionDispositivo) {
        this.descripcionDispositivo = descripcionDispositivo;
    }

    public String getActivoDispositivo() {
        return activoDispositivo;
    }

    public void setActivoDispositivo(String activoDispositivo) {
        this.activoDispositivo = activoDispositivo;
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }

    public String getUbicacionArea() {
        return ubicacionArea;
    }

    public void setUbicacionArea(String ubicacionArea) {
        this.ubicacionArea = ubicacionArea;
    }
}
