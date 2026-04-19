package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "permisos_personalizados")
@IdClass(PermisoPersonalizadoId.class)
public class PermisoPersonalizado {

    @Id
    @Column(name = "matricula")
    private Integer matricula;

    @Id
    @Column(name = "id_accion")
    private Integer idAccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matricula", insertable = false, updatable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_accion", insertable = false, updatable = false)
    private AccionAdmin accionAdmin;

    public PermisoPersonalizado() {
    }

    public PermisoPersonalizado(Integer matricula, Integer idAccion) {
        this.matricula = matricula;
        this.idAccion = idAccion;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public Integer getIdAccion() {
        return idAccion;
    }

    public void setIdAccion(Integer idAccion) {
        this.idAccion = idAccion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public AccionAdmin getAccionAdmin() {
        return accionAdmin;
    }

    public void setAccionAdmin(AccionAdmin accionAdmin) {
        this.accionAdmin = accionAdmin;
    }
}
