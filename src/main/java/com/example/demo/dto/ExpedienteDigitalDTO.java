package com.example.demo.dto;

import java.time.LocalDateTime;

public class ExpedienteDigitalDTO {

    private Integer idArchivo;
    private Integer matricula;
    private String urlPdf;
    private String tipoDoc;
    private LocalDateTime fechaCarga;

    public ExpedienteDigitalDTO() {
    }

    public ExpedienteDigitalDTO(Integer idArchivo, Integer matricula, String urlPdf, String tipoDoc, LocalDateTime fechaCarga) {
        this.idArchivo = idArchivo;
        this.matricula = matricula;
        this.urlPdf = urlPdf;
        this.tipoDoc = tipoDoc;
        this.fechaCarga = fechaCarga;
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
}
