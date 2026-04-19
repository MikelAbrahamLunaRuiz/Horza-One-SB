package com.example.demo.model;

import java.io.Serializable;
import java.util.Objects;

public class VinculoTutorId implements Serializable {

    private Integer idTutor;
    private Integer matriculaEstudiante;

    public VinculoTutorId() {
    }

    public VinculoTutorId(Integer idTutor, Integer matriculaEstudiante) {
        this.idTutor = idTutor;
        this.matriculaEstudiante = matriculaEstudiante;
    }

    public Integer getIdTutor() {
        return idTutor;
    }

    public void setIdTutor(Integer idTutor) {
        this.idTutor = idTutor;
    }

    public Integer getMatriculaEstudiante() {
        return matriculaEstudiante;
    }

    public void setMatriculaEstudiante(Integer matriculaEstudiante) {
        this.matriculaEstudiante = matriculaEstudiante;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VinculoTutorId that)) return false;
        return Objects.equals(idTutor, that.idTutor) &&
                Objects.equals(matriculaEstudiante, that.matriculaEstudiante);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTutor, matriculaEstudiante);
    }
}
