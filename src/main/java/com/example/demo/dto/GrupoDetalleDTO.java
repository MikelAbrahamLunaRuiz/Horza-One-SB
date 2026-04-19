package com.example.demo.dto;

import java.util.List;

public class GrupoDetalleDTO {

    private Integer idGrupo;
    private String nombreGrupo;
    private Integer matriculaAdministrador;
    private String nombreAdministrador;
    private List<GrupoIntegranteDTO> integrantes;

    public GrupoDetalleDTO() {
    }

    public GrupoDetalleDTO(Integer idGrupo, String nombreGrupo, Integer matriculaAdministrador,
                           String nombreAdministrador, List<GrupoIntegranteDTO> integrantes) {
        this.idGrupo = idGrupo;
        this.nombreGrupo = nombreGrupo;
        this.matriculaAdministrador = matriculaAdministrador;
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

    public Integer getMatriculaAdministrador() {
        return matriculaAdministrador;
    }

    public void setMatriculaAdministrador(Integer matriculaAdministrador) {
        this.matriculaAdministrador = matriculaAdministrador;
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
