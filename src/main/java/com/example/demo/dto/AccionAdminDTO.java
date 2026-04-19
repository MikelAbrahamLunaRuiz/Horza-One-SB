package com.example.demo.dto;

public class AccionAdminDTO {

    private Integer idAccion;
    private String nombreAccion;

    public AccionAdminDTO() {
    }

    public AccionAdminDTO(Integer idAccion, String nombreAccion) {
        this.idAccion = idAccion;
        this.nombreAccion = nombreAccion;
    }

    public Integer getIdAccion() {
        return idAccion;
    }

    public void setIdAccion(Integer idAccion) {
        this.idAccion = idAccion;
    }

    public String getNombreAccion() {
        return nombreAccion;
    }

    public void setNombreAccion(String nombreAccion) {
        this.nombreAccion = nombreAccion;
    }
}
