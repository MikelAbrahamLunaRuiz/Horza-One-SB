package com.example.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "expediente_digital")
public class ExpedienteDigital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_archivo")
    private Integer idArchivo;

    @Column(name = "matricula", nullable = false)
    private Integer matricula;

    @Column(name = "url_pdf", nullable = false, length = 500)
    private String urlPdf;

    @Column(name = "tipo_doc", nullable = false, length = 80)
    private String tipoDoc;

    @Column(name = "fecha_carga")
    private LocalDateTime fechaCarga;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matricula", insertable = false, updatable = false)
    private Usuario usuario;

    public ExpedienteDigital() {
    }

    public ExpedienteDigital(Integer idArchivo, Integer matricula, String urlPdf, String tipoDoc, LocalDateTime fechaCarga) {
        this.idArchivo = idArchivo;
        this.matricula = matricula;
        this.urlPdf = urlPdf;
        this.tipoDoc = tipoDoc;
        this.fechaCarga = fechaCarga;
    }

    @PrePersist
    public void prePersist() {
        if (this.fechaCarga == null) {
            this.fechaCarga = LocalDateTime.now();
        }
    }

    public Integer getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(Integer idArchivo) {
        this.idArchivo = idArchivo;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public String getUrlPdf() {
        return urlPdf;
    }

    public void setUrlPdf(String urlPdf) {
        this.urlPdf = urlPdf;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public LocalDateTime getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(LocalDateTime fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
