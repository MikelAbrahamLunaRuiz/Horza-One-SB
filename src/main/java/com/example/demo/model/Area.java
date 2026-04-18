package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "area")
public class Area {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_area")
    private Integer idArea;
    
    @Column(name = "nombre_area", nullable = false, length = 60)
    private String nombreArea;
    
    @Column(name = "descripcion_area", columnDefinition = "TEXT")
    private String descripcionArea;
    
    @Column(name = "activo_area", columnDefinition = "ENUM('Activo', 'Inactivo')")
    private String activoArea;
    
    @Column(name = "ubicacion", length = 100)
    private String ubicacion;

    // Constructor sin argumentos
    public Area() {
    }

    // Constructor con todos los argumentos
    public Area(Integer idArea, String nombreArea, String descripcionArea, String activoArea, String ubicacion) {
        this.idArea = idArea;
        this.nombreArea = nombreArea;
        this.descripcionArea = descripcionArea;
        this.activoArea = activoArea;
        this.ubicacion = ubicacion;
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
