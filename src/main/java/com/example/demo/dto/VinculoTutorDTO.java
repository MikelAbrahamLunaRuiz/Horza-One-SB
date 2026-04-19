package com.example.demo.dto;

public class VinculoTutorDTO {

    private Integer idTutor;
    private Integer matriculaEstudiante;
    private String nombreTutor;
    private String nombreEstudiante;

    public VinculoTutorDTO() {
    }

    public VinculoTutorDTO(Integer idTutor, Integer matriculaEstudiante, String nombreTutor, String nombreEstudiante) {
        this.idTutor = idTutor;
        this.matriculaEstudiante = matriculaEstudiante;
        this.nombreTutor = nombreTutor;
        this.nombreEstudiante = nombreEstudiante;
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

    public String getNombreTutor() {
        return nombreTutor;
    }

    public void setNombreTutor(String nombreTutor) {
        this.nombreTutor = nombreTutor;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }
}
