package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entidad HORARIO - Representa una semana completa (contiene 7 días)
 * INDEPENDIENTE - No tiene FK a calendario
 * Un horario puede estar en MUCHOS calendarios (relación N:1 desde Calendario)
 */
@Entity
@Table(name = "horario")
public class Horario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario")
    private Integer idHorario;
    
    @Column(name = "nombre_horario", length = 60, nullable = false)
    private String nombreHorario;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "activo_horario", columnDefinition = "ENUM('Activo', 'Inactivo')")
    private String activoHorario;
    
    @OneToMany(mappedBy = "horario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiaHorario> diasHorario = new ArrayList<>();

    // Constructor sin argumentos
    public Horario() {
    }

    // Constructor con todos los argumentos
    public Horario(Integer idHorario, String nombreHorario, String descripcion, String activoHorario) {
        this.idHorario = idHorario;
        this.nombreHorario = nombreHorario;
        this.descripcion = descripcion;
        this.activoHorario = activoHorario;
    }

    // Getters y Setters
    public Integer getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Integer idHorario) {
        this.idHorario = idHorario;
    }

    public String getNombreHorario() {
        return nombreHorario;
    }

    public void setNombreHorario(String nombreHorario) {
        this.nombreHorario = nombreHorario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getActivoHorario() {
        return activoHorario;
    }

    public void setActivoHorario(String activoHorario) {
        this.activoHorario = activoHorario;
    }

    public List<DiaHorario> getDiasHorario() {
        return diasHorario;
    }

    public void setDiasHorario(List<DiaHorario> diasHorario) {
        this.diasHorario = diasHorario;
    }
}
