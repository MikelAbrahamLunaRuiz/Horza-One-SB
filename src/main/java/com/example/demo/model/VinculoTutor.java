package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "vinculo_tutor")
@IdClass(VinculoTutorId.class)
public class VinculoTutor {

    @Id
    @Column(name = "id_tutor")
    private Integer idTutor;

    @Id
    @Column(name = "matricula_estudiante")
    private Integer matriculaEstudiante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tutor", insertable = false, updatable = false)
    private Tutor tutor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matricula_estudiante", insertable = false, updatable = false)
    private Usuario estudiante;

    public VinculoTutor() {
    }

    public VinculoTutor(Integer idTutor, Integer matriculaEstudiante) {
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

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public Usuario getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Usuario estudiante) {
        this.estudiante = estudiante;
    }
}
