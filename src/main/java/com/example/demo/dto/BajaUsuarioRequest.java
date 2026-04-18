package com.example.demo.dto;

public class BajaUsuarioRequest {
    private Integer matricula;
    private Integer matriculaAdmin;
    private String contrasenaAdmin;
    private String motivo;

    public BajaUsuarioRequest() {
    }

    public BajaUsuarioRequest(Integer matricula, Integer matriculaAdmin, String contrasenaAdmin, String motivo) {
        this.matricula = matricula;
        this.matriculaAdmin = matriculaAdmin;
        this.contrasenaAdmin = contrasenaAdmin;
        this.motivo = motivo;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public Integer getMatriculaAdmin() {
        return matriculaAdmin;
    }

    public void setMatriculaAdmin(Integer matriculaAdmin) {
        this.matriculaAdmin = matriculaAdmin;
    }

    public String getContrasenaAdmin() {
        return contrasenaAdmin;
    }

    public void setContrasenaAdmin(String contrasenaAdmin) {
        this.contrasenaAdmin = contrasenaAdmin;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
