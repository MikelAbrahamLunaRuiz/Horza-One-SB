package com.example.demo.dto;

public class GrupoIntegranteDTO {

    private Integer matricula;
    private String nombreCompleto;
    private String nombreRolSistema;
    private String rolGrupo;
    private String fotoPerfil;

    public GrupoIntegranteDTO() {
    }

    public GrupoIntegranteDTO(Integer matricula, String nombreCompleto, String nombreRolSistema, String rolGrupo, String fotoPerfil) {
        this.matricula = matricula;
        this.nombreCompleto = nombreCompleto;
        this.nombreRolSistema = nombreRolSistema;
        this.rolGrupo = rolGrupo;
        this.fotoPerfil = fotoPerfil;
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

    public String getNombreRolSistema() {
        return nombreRolSistema;
    }

    public void setNombreRolSistema(String nombreRolSistema) {
        this.nombreRolSistema = nombreRolSistema;
    }

    public String getRolGrupo() {
        return rolGrupo;
    }

    public void setRolGrupo(String rolGrupo) {
        this.rolGrupo = rolGrupo;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }
}
