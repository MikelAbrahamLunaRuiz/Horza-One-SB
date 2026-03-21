package com.example.demo.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "rol_usuario")
public class RolUsuario {
    
    @EmbeddedId
    private RolUsuarioId id;
    
    @ManyToOne
    @MapsId("matricula")
    @JoinColumn(name = "matricula")
    private Usuario usuario;
    
    @ManyToOne
    @MapsId("idRol")
    @JoinColumn(name = "id_rol")
    private Rol rol;

    // Constructor sin argumentos
    public RolUsuario() {
    }

    // Constructor con todos los argumentos
    public RolUsuario(RolUsuarioId id, Usuario usuario, Rol rol) {
        this.id = id;
        this.usuario = usuario;
        this.rol = rol;
    }

    // Getters y Setters
    public RolUsuarioId getId() {
        return id;
    }

    public void setId(RolUsuarioId id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}

@Embeddable
class RolUsuarioId implements Serializable {
    @Column(name = "matricula")
    private Integer matricula;
    
    @Column(name = "id_rol")
    private Integer idRol;

    // Constructor sin argumentos
    public RolUsuarioId() {
    }

    // Constructor con todos los argumentos
    public RolUsuarioId(Integer matricula, Integer idRol) {
        this.matricula = matricula;
        this.idRol = idRol;
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
}
