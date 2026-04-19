package com.example.demo.model;

import java.io.Serializable;
import java.util.Objects;

public class PermisoPersonalizadoId implements Serializable {

    private Integer matricula;
    private Integer idAccion;

    public PermisoPersonalizadoId() {
    }

    public PermisoPersonalizadoId(Integer matricula, Integer idAccion) {
        this.matricula = matricula;
        this.idAccion = idAccion;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PermisoPersonalizadoId that)) return false;
        return Objects.equals(matricula, that.matricula) && Objects.equals(idAccion, that.idAccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricula, idAccion);
    }
}
