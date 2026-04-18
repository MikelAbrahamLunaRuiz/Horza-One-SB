package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bitacora")
public class Bitacora {
    
    @Id
    @Column(name = "id_bitacora")
    private Integer idBitacora;
    
    @ManyToOne
    @JoinColumn(name = "matricula", nullable = false)
    private Usuario usuario;
    
    @Column(name = "num_entradas")
    private Integer numEntradas;
    
    @Column(name = "num_inasistencias")
    private Integer numInasistencias;
    
    @Column(name = "num_retardos")
    private Integer numRetardos;
    
    @Column(name = "num_entradas_anticipadas")
    private Integer numEntradasAnticipadas;

    // Constructor sin argumentos
    public Bitacora() {
    }

    // Constructor con todos los argumentos
    public Bitacora(Integer idBitacora, Usuario usuario, Integer numEntradas, Integer numInasistencias, 
                    Integer numRetardos, Integer numEntradasAnticipadas) {
        this.idBitacora = idBitacora;
        this.usuario = usuario;
        this.numEntradas = numEntradas;
        this.numInasistencias = numInasistencias;
        this.numRetardos = numRetardos;
        this.numEntradasAnticipadas = numEntradasAnticipadas;
    }

    // Getters y Setters
    public Integer getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(Integer idBitacora) {
        this.idBitacora = idBitacora;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getNumEntradas() {
        return numEntradas;
    }

    public void setNumEntradas(Integer numEntradas) {
        this.numEntradas = numEntradas;
    }

    public Integer getNumInasistencias() {
        return numInasistencias;
    }

    public void setNumInasistencias(Integer numInasistencias) {
        this.numInasistencias = numInasistencias;
    }

    public Integer getNumRetardos() {
        return numRetardos;
    }

    public void setNumRetardos(Integer numRetardos) {
        this.numRetardos = numRetardos;
    }

    public Integer getNumEntradasAnticipadas() {
        return numEntradasAnticipadas;
    }

    public void setNumEntradasAnticipadas(Integer numEntradasAnticipadas) {
        this.numEntradasAnticipadas = numEntradasAnticipadas;
    }
}
