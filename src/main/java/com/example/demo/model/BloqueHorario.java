package com.example.demo.model;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidad BLOQUES_HORARIO - Bloques independientes reutilizables
 * Los bloques YA NO están vinculados directamente a un día específico
 * Se ASIGNAN a días mediante la tabla intermedia BLOQUE_DIA_ASIGNACION
 * Esto permite que el mismo bloque se use en múltiples días/horarios
 */
@Entity
@Table(name = "bloques_horario")
public class BloqueHorario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bloque")
    private Integer idBloque;
    
    @ManyToOne
    @JoinColumn(name = "id_area", nullable = false)
    private Area area;
    
    @Column(name = "nombre_bloque", length = 60, nullable = false)
    private String nombreBloque;
    
    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;
    
    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;
    
    @Column(name = "activo", length = 10)
    private String activo = "Activo";

    // Constructor sin argumentos
    public BloqueHorario() {
    }

    // Constructor con todos los argumentos
    public BloqueHorario(Integer idBloque, Area area, String nombreBloque, 
                         LocalTime horaInicio, LocalTime horaFin, String activo) {
        this.idBloque = idBloque;
        this.area = area;
        this.nombreBloque = nombreBloque;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.activo = activo;
    }

    // Getters y Setters
    public Integer getIdBloque() {
        return idBloque;
    }

    public void setIdBloque(Integer idBloque) {
        this.idBloque = idBloque;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String getNombreBloque() {
        return nombreBloque;
    }

    public void setNombreBloque(String nombreBloque) {
        this.nombreBloque = nombreBloque;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }
}
