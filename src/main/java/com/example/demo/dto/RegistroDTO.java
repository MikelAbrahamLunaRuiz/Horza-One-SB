package com.example.demo.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class RegistroDTO {
    private Integer idRegistro;
    private Integer matricula;
    private Integer idBitacora;
    private Integer idDispositivo;
    private Integer idArea;
    private String nombreArea;
    private String nombreUsuario;
    private String tipoRegistro;
    private LocalDate fecha;
    private LocalTime hora;
    private String observacion;
    private String estadoRegistro;

    // Constructor sin argumentos
    public RegistroDTO() {
    }

    // Constructor con todos los argumentos
    public RegistroDTO(Integer idRegistro, Integer matricula, Integer idBitacora, Integer idDispositivo, 
                       Integer idArea, String nombreArea, String nombreUsuario, String tipoRegistro, 
                       LocalDate fecha, LocalTime hora, String observacion, String estadoRegistro) {
        this.idRegistro = idRegistro;
        this.matricula = matricula;
        this.idBitacora = idBitacora;
        this.idDispositivo = idDispositivo;
        this.idArea = idArea;
        this.nombreArea = nombreArea;
        this.nombreUsuario = nombreUsuario;
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

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public Integer getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(Integer idBitacora) {
        this.idBitacora = idBitacora;
    }

    public Integer getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(Integer idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
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

    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
