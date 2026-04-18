package com.example.demo.dto;

import java.time.LocalDate;

public class UsuarioDTO {
    private Integer matricula;
    private Integer idRol;
    private Integer idCalendario; // ID del calendario asignado
    private String rfc;
    private String curp;
    private LocalDate fechaAlta;
    private String nombreUsuario;
    private String apellidoPaternoUsuario;
    private String apellidoMaternoUsuario;
    private String telefono;
    private String tipoContrato;
    private String correo;
    private String activo;
    private String cpUsuario;
    private String calleUsuario;
    private String contrasena;

    // Constructor sin argumentos
    public UsuarioDTO() {
    }

    // Constructor con todos los argumentos
    public UsuarioDTO(Integer matricula, Integer idRol, Integer idCalendario, String rfc, String curp, LocalDate fechaAlta, String nombreUsuario, String apellidoPaternoUsuario, String apellidoMaternoUsuario, String telefono, String tipoContrato, String correo, String activo, String cpUsuario, String calleUsuario, String contrasena) {
        this.matricula = matricula;
        this.idRol = idRol;
        this.idCalendario = idCalendario;
        this.rfc = rfc;
        this.curp = curp;
        this.fechaAlta = fechaAlta;
        this.nombreUsuario = nombreUsuario;
        this.apellidoPaternoUsuario = apellidoPaternoUsuario;
        this.apellidoMaternoUsuario = apellidoMaternoUsuario;
        this.telefono = telefono;
        this.tipoContrato = tipoContrato;
        this.correo = correo;
        this.activo = activo;
        this.cpUsuario = cpUsuario;
        this.calleUsuario = calleUsuario;
        this.contrasena = contrasena;
    }

    // Getters y Setters
    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public Integer getIdCalendario() {
        return idCalendario;
    }

    public void setIdCalendario(Integer idCalendario) {
        this.idCalendario = idCalendario;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoPaternoUsuario() {
        return apellidoPaternoUsuario;
    }

    public void setApellidoPaternoUsuario(String apellidoPaternoUsuario) {
        this.apellidoPaternoUsuario = apellidoPaternoUsuario;
    }

    public String getApellidoMaternoUsuario() {
        return apellidoMaternoUsuario;
    }

    public void setApellidoMaternoUsuario(String apellidoMaternoUsuario) {
        this.apellidoMaternoUsuario = apellidoMaternoUsuario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getCpUsuario() {
        return cpUsuario;
    }

    public void setCpUsuario(String cpUsuario) {
        this.cpUsuario = cpUsuario;
    }

    public String getCalleUsuario() {
        return calleUsuario;
    }

    public void setCalleUsuario(String calleUsuario) {
        this.calleUsuario = calleUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
