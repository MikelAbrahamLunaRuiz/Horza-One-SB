package com.example.demo.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios_calendario")
public class UsuarioCalendario {
    
    @EmbeddedId
    private UsuarioCalendarioId id;
    
    @ManyToOne
    @MapsId("matricula")
    @JoinColumn(name = "matricula")
    private Usuario usuario;
    
    @ManyToOne
    @MapsId("idCalendario")
    @JoinColumn(name = "id_calendario")
    private Calendario calendario;

    public UsuarioCalendario() {
    }

    public UsuarioCalendario(UsuarioCalendarioId id, Usuario usuario, Calendario calendario) {
        this.id = id;
        this.usuario = usuario;
        this.calendario = calendario;
    }

    public UsuarioCalendarioId getId() {
        return id;
    }

    public void setId(UsuarioCalendarioId id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Calendario getCalendario() {
        return calendario;
    }

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }
}
