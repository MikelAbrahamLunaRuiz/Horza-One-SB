package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "registro")
public class Registro {
    
    @Id
    @Column(name = "id_registro")
    private Integer idRegistro;
    
    @ManyToOne
    @JoinColumn(name = "matricula", nullable = false)
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "id_bitacora", nullable = false)
    private Bitacora bitacora;
    
    @ManyToOne
    @JoinColumn(name = "id_dispositivo", nullable = false)
    private Dispositivo dispositivo;
    
    @ManyToOne
    @JoinColumn(name = "id_area", nullable = false)
    private Area area;
    
    @Column(name = "tipo_registro", nullable = false, columnDefinition = "ENUM('Entrada', 'Salida')")
    private String tipoRegistro;
    
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;
    
    @Column(name = "hora", nullable = false)
    private LocalTime hora;
    
    @Column(name = "observacion", columnDefinition = "TEXT")
    private String observacion;
    
    @Column(name = "estado_registro", columnDefinition = "ENUM('Puntual', 'Retardo', 'Anticipado')")
    private String estadoRegistro;

    // Constructor sin argumentos
    public Registro() {
    }

    // Constructor con todos los argumentos
    public Registro(Integer idRegistro, Usuario usuario, Bitacora bitacora, Dispositivo dispositivo, 
                    Area area, String tipoRegistro, LocalDate fecha, LocalTime hora, 
                    String observacion, String estadoRegistro) {
        this.idRegistro = idRegistro;
        this.usuario = usuario;
        this.bitacora = bitacora;
        this.dispositivo = dispositivo;
        this.area = area;
        this.tipoRegistro = tipoRegistro;
        this.fecha = fecha;
        this.hora = hora;
        this.observacion = observacion;
        this.estadoRegistro = estadoRegistro;
    }

    // Getters y Setters
    public Integer getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Bitacora getBitacora() {
        return bitacora;
    }

    public void setBitacora(Bitacora bitacora) {
        this.bitacora = bitacora;
    }

    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(String estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }
}
