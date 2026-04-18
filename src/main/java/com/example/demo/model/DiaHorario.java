package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * Entidad DIA_HORARIO - Relación entre HORARIO y DIAS_SEMANA
 * Cada HORARIO debe tener exactamente 7 registros (uno por cada día fijo)
 */
@Entity
@Table(name = "dia_horario", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"id_horario", "id_dia"}))
public class DiaHorario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dia_horario")
    private Integer idDiaHorario;
    
    @ManyToOne
    @JoinColumn(name = "id_horario", nullable = false)
    private Horario horario;
    
    @ManyToOne
    @JoinColumn(name = "id_dia", nullable = false)
    private DiaSemana diaSemana;

    // Constructor sin argumentos
    public DiaHorario() {
    }

    // Constructor con todos los argumentos
    public DiaHorario(Integer idDiaHorario, Horario horario, DiaSemana diaSemana) {
        this.idDiaHorario = idDiaHorario;
        this.horario = horario;
        this.diaSemana = diaSemana;
    }

    // Getters y Setters
    public Integer getIdDiaHorario() {
        return idDiaHorario;
    }

    public void setIdDiaHorario(Integer idDiaHorario) {
        this.idDiaHorario = idDiaHorario;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }
}
