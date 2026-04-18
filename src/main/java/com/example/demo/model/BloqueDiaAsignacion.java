package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidad BLOQUE_DIA_ASIGNACION - Tabla intermedia para relación muchos a muchos
 * Relaciona bloques maestros con días de horario
 * Un bloque puede estar asignado a múltiples días
 * Un día puede tener múltiples bloques asignados
 */
@Entity
@Table(name = "bloque_dia_asignacion")
public class BloqueDiaAsignacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asignacion")
    private Integer idAsignacion;
    
    @ManyToOne
    @JoinColumn(name = "id_dia_horario", nullable = false)
    private DiaHorario diaHorario;
    
    @ManyToOne
    @JoinColumn(name = "id_bloque", nullable = false)
    private BloqueHorario bloqueHorario;

    // Constructor sin argumentos
    public BloqueDiaAsignacion() {
    }

    // Constructor con argumentos
    public BloqueDiaAsignacion(DiaHorario diaHorario, BloqueHorario bloqueHorario) {
        this.diaHorario = diaHorario;
        this.bloqueHorario = bloqueHorario;
    }

    // Getters y Setters
    public Integer getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(Integer idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    public DiaHorario getDiaHorario() {
        return diaHorario;
    }

    public void setDiaHorario(DiaHorario diaHorario) {
        this.diaHorario = diaHorario;
    }

    public BloqueHorario getBloqueHorario() {
        return bloqueHorario;
    }

    public void setBloqueHorario(BloqueHorario bloqueHorario) {
        this.bloqueHorario = bloqueHorario;
    }
}
