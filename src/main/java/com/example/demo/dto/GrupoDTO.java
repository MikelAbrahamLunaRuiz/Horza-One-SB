package com.example.demo.dto;

public class GrupoDTO {

    private Integer idGrupo;
    private String nombreGrupo;
    private Integer matriculaLider;
    private String nombreLider;

    public GrupoDTO() {
    }

    public GrupoDTO(Integer idGrupo, String nombreGrupo, Integer matriculaLider, String nombreLider) {
        this.idGrupo = idGrupo;
        this.nombreGrupo = nombreGrupo;
        this.matriculaLider = matriculaLider;
        this.nombreLider = nombreLider;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public Integer getMatriculaLider() {
        return matriculaLider;
    }

    public void setMatriculaLider(Integer matriculaLider) {
        this.matriculaLider = matriculaLider;
    }

    public String getNombreLider() {
        return nombreLider;
    }

    public void setNombreLider(String nombreLider) {
        this.nombreLider = nombreLider;
    }
}
