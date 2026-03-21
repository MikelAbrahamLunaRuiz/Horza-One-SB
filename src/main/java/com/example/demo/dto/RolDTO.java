package com.example.demo.dto;

public class RolDTO {
    private Integer idRol;
    private String nombreRol;
    private String tipoPermiso;

    // Constructor sin argumentos
    public RolDTO() {
    }

    // Constructor con todos los argumentos
    public RolDTO(Integer idRol, String nombreRol, String tipoPermiso) {
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
