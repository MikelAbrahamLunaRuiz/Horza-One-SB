package com.example.demo.model;

import java.io.Serializable;
import java.util.Objects;

public class GrupoIntegranteId implements Serializable {

    private Integer idGrupo;
    private Integer matricula;

    public GrupoIntegranteId() {
    }

    public GrupoIntegranteId(Integer idGrupo, Integer matricula) {
        this.idGrupo = idGrupo;
        this.matricula = matricula;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GrupoIntegranteId that)) return false;
        return Objects.equals(idGrupo, that.idGrupo) && Objects.equals(matricula, that.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGrupo, matricula);
    }
}
