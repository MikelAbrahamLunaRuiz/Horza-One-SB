package com.example.demo.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class LoginResponse {
    private Boolean success;
    private String mensaje;
    private Integer matricula;
    private String nombreCompleto;
    private String nombreRol;
    private Integer idRol;
    private String tipoPermiso;

    public LoginResponse() {
    }

    public LoginResponse(Boolean success, String mensaje, Integer matricula, String nombreCompleto, String nombreRol, Integer idRol, String tipoPermiso) {
        this.success = success;
        this.mensaje = mensaje;
        this.matricula = matricula;
        this.nombreCompleto = nombreCompleto;
        this.nombreRol = nombreRol;
        this.idRol = idRol;
        this.tipoPermiso = tipoPermiso;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getTipoPermiso() {
        return tipoPermiso;
    }

    public void setTipoPermiso(String tipoPermiso) {
        this.tipoPermiso = tipoPermiso;
    }
}