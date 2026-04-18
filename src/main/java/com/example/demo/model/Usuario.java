package com.example.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @Column(name = "matricula")
    private Integer matricula;

    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    @Column(name = "rfc", nullable = false, length = 20)
    private String rfc;

    @Column(name = "curp", nullable = false, length = 20)
    private String curp;

    @Column(name = "fecha_alta")
    private LocalDate fechaAlta;

    @Column(name = "nombre_usuario", nullable = false, length = 40)
    private String nombreUsuario;

    @Column(name = "apellido_paterno_usuario", nullable = false, length = 20)
    private String apellidoPaternoUsuario;

    @Column(name = "apellido_materno_usuario", nullable = false, length = 20)
    private String apellidoMaternoUsuario;

    @Column(name = "telefono", length = 12)
    private String telefono;

    @Column(name = "tipo_contrato", length = 40)
    private String tipoContrato;

    @Column(name = "correo", length = 50)
    private String correo;

    @Column(name = "activo", columnDefinition = "ENUM('Activo', 'Inactivo')")
    private String activo;

    @Column(name = "cp_usuario", length = 7)
    private String cpUsuario;

    @Column(name = "calle_usuario", length = 40)
    private String calleUsuario;

    @Column(name = "contrasenia", length = 12)
    private String contrasena;

    @Column(name = "estado_presencia", columnDefinition = "ENUM('Dentro', 'Fuera')")
    private String estadoPresencia = "Fuera"; // Valor por defecto

    public Usuario() {
        this.estadoPresencia = "Fuera"; // Inicializar por defecto
    }

    public Usuario(Integer matricula, Rol rol, String rfc, String curp, LocalDate fechaAlta,
                   String nombreUsuario, String apellidoPaternoUsuario, String apellidoMaternoUsuario,
                   String telefono, String tipoContrato, String correo, String activo,
                   String cpUsuario, String calleUsuario, String contrasena) {
        this.matricula = matricula;
        this.rol = rol;
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
        this.estadoPresencia = "Fuera"; // Inicializar por defecto al crear usuario
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
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

    public String getEstadoPresencia() {
        return estadoPresencia;
    }

    public void setEstadoPresencia(String estadoPresencia) {
        this.estadoPresencia = estadoPresencia;
    }
}
