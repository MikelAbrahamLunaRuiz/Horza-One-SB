package com.example.demo.dto;

import java.util.List;

public class GrupoDetalleDTO {

    private Integer idGrupo;
    private String nombreGrupo;
    private Integer matriculaLider;
    private String nombreLider;
    private String nombreCapitan;
    private String nombreAdministrador;
    private List<GrupoIntegranteDTO> integrantes;

    public GrupoDetalleDTO() {
    }

    public GrupoDetalleDTO(Integer idGrupo, String nombreGrupo, Integer matriculaLider, String nombreLider,
                           String nombreCapitan, String nombreAdministrador, List<GrupoIntegranteDTO> integrantes) {
        this.idGrupo = idGrupo;
        this.nombreGrupo = nombreGrupo;
        this.matriculaLider = matriculaLider;
        this.nombreLider = nombreLider;
        this.nombreCapitan = nombreCapitan;
        this.nombreAdministrador = nombreAdministrador;
        this.integrantes = integrantes;
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

    public String getNombreCapitan() {
        return nombreCapitan;
    }

    public void setNombreCapitan(String nombreCapitan) {
        this.nombreCapitan = nombreCapitan;
    }

    public String getNombreAdministrador() {
        return nombreAdministrador;
    }

    public void setNombreAdministrador(String nombreAdministrador) {
        this.nombreAdministrador = nombreAdministrador;
    }

    public List<GrupoIntegranteDTO> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(List<GrupoIntegranteDTO> integrantes) {
        this.integrantes = integrantes;
    }
}
