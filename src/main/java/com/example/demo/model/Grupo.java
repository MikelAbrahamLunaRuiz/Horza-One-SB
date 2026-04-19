package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "grupos")
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grupo")
    private Integer idGrupo;

    @Column(name = "nombre_grupo", nullable = false, length = 100)
    private String nombreGrupo;

    @Column(name = "matricula_lider", nullable = false)
    private Integer matriculaLider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matricula_lider", insertable = false, updatable = false)
    private Usuario lider;

    public Grupo() {
    }

    public Grupo(Integer idGrupo, String nombreGrupo, Integer matriculaLider) {
        this.idGrupo = idGrupo;
        this.nombreGrupo = nombreGrupo;
        this.matriculaLider = matriculaLider;
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

    public Usuario getLider() {
        return lider;
    }

    public void setLider(Usuario lider) {
        this.lider = lider;
    }
}
