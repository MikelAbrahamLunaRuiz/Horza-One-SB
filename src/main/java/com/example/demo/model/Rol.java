package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rol")
public class Rol {
    
    @Id
    @Column(name = "id_rol")
    private Integer idRol;
    
    @Column(name = "nombre_rol", nullable = false, length = 50)
    private String nombreRol;
    
    @Column(name = "tipo_permiso", nullable = false, columnDefinition = "ENUM('ADMIN','PERSONAL')")
    private String tipoPermiso;

    // Constructor sin argumentos
    public Rol() {
    }

    // Constructor con todos los argumentos
    public Rol(Integer idRol, String nombreRol, String tipoPermiso) {
        this.idRol = idRol;
        this.nombreRol = nombreRol;
        this.tipoPermiso = tipoPermiso;
    }

    // Getters y Setters
    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getTipoPermiso() {
        return tipoPermiso;
    }

    public void setTipoPermiso(String tipoPermiso) {
        this.tipoPermiso = tipoPermiso;
    }
}
