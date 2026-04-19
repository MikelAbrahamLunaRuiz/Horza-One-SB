package com.example.demo.dto;

public class PermisoPersonalizadoDTO {

    private Integer matricula;
    private Integer idAccion;
    private String nombreAccion;

    public PermisoPersonalizadoDTO() {
    }

    public PermisoPersonalizadoDTO(Integer matricula, Integer idAccion, String nombreAccion) {
        this.matricula = matricula;
        this.idAccion = idAccion;
        this.nombreAccion = nombreAccion;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
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
