package com.example.demo.model;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UsuarioCalendarioId implements Serializable {
    @Column(name = "matricula")
    private Integer matricula;
    
    @Column(name = "id_calendario")
    private Integer idCalendario;

    public UsuarioCalendarioId() {
    }

    public UsuarioCalendarioId(Integer matricula, Integer idCalendario) {
        this.matricula = matricula;
        this.idCalendario = idCalendario;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public Integer getIdCalendario() {
        return idCalendario;
    }

    public void setIdCalendario(Integer idCalendario) {
        this.idCalendario = idCalendario;
    }
}
