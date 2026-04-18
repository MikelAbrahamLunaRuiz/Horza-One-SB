package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "dispositivo")
public class Dispositivo {
    
    @Id
    @Column(name = "id_dispositivo")
    private Integer idDispositivo;
    
    @ManyToOne
    @JoinColumn(name = "id_area", nullable = false)
    private Area area;
    
    @Column(name = "nombre_dispositivo", nullable = false, length = 100)
    private String nombreDispositivo;
    
    @Column(name = "descripcion_dispositivo", columnDefinition = "TEXT")
    private String descripcionDispositivo;
    
    @Column(name = "activo_dispositivo", columnDefinition = "ENUM('Activo', 'Inactivo')")
    private String activoDispositivo;

    // Constructor sin argumentos
    public Dispositivo() {
    }

    // Constructor con todos los argumentos (para actualizaciones)
    public Dispositivo(Integer idDispositivo, Area area, String nombreDispositivo, 
                       String descripcionDispositivo, String activoDispositivo) {
        this.idDispositivo = idDispositivo;
        this.area = area;
        this.nombreDispositivo = nombreDispositivo;
        this.descripcionDispositivo = descripcionDispositivo;
        this.activoDispositivo = activoDispositivo;
    }

    // Constructor sin ID (para nuevas creaciones)
    public Dispositivo(Area area, String nombreDispositivo, 
                       String descripcionDispositivo, String activoDispositivo) {
        this.area = area;
        this.nombreDispositivo = nombreDispositivo;
        this.descripcionDispositivo = descripcionDispositivo;
        this.activoDispositivo = activoDispositivo;
    }

    // Getters y Setters
    public Integer getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(Integer idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
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
}
