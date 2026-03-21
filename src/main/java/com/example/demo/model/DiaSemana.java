package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad para el catálogo DIAS_SEMANA (7 registros fijos)
 * Representa los días de la semana que SIEMPRE existen en la base de datos
 */
@Entity
@Table(name = "dias_semana")
public class DiaSemana {
    
    @Id
    @Column(name = "id_dia")
    private Integer idDia; // 1=Lunes, 2=Martes, ..., 7=Domingo
    
    @Column(name = "nombre_dia", nullable = false, length = 20)
    private String nombreDia; // "Lunes", "Martes", etc.
    
    @Column(name = "orden_dia", nullable = false)
    private Integer ordenDia; // Orden en la semana (1-7)

    // Constructor sin argumentos
    public DiaSemana() {
    }

    // Constructor con todos los argumentos
    public DiaSemana(Integer idDia, String nombreDia, Integer ordenDia) {
        this.idDia = idDia;
        this.nombreDia = nombreDia;
        this.ordenDia = ordenDia;
    }

    // Getters y Setters
    public Integer getIdDia() {
        return idDia;
    }

    public void setIdDia(Integer idDia) {
        this.idDia = idDia;
    }

    public String getNombreDia() {
        return nombreDia;
    }

    public void setNombreDia(String nombreDia) {
        this.nombreDia = nombreDia;
    }

    public Integer getOrdenDia() {
        return ordenDia;
    }

    public void setOrdenDia(Integer ordenDia) {
        this.ordenDia = ordenDia;
    }
}
