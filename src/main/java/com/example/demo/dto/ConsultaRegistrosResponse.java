package com.example.demo.dto;

import java.util.List;

public class ConsultaRegistrosResponse {
    private Integer matricula;
    private String nombreCompleto;
    private Integer totalRegistros;
    private Integer totalEntradas;
    private Integer totalSalidas;
    private Integer totalPuntuales;
    private Integer totalRetardos;
    private Integer totalAnticipados;
    private List<RegistroDTO> registros;

    public ConsultaRegistrosResponse() {
    }

    public ConsultaRegistrosResponse(Integer matricula, String nombreCompleto, Integer totalRegistros, Integer totalEntradas, Integer totalSalidas, Integer totalPuntuales, Integer totalRetardos, Integer totalAnticipados, List<RegistroDTO> registros) {
        this.matricula = matricula;
        this.nombreCompleto = nombreCompleto;
        this.totalRegistros = totalRegistros;
        this.totalEntradas = totalEntradas;
        this.totalSalidas = totalSalidas;
        this.totalPuntuales = totalPuntuales;
        this.totalRetardos = totalRetardos;
        this.totalAnticipados = totalAnticipados;
        this.registros = registros;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Integer getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(Integer totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    public Integer getTotalEntradas() {
        return totalEntradas;
    }

    public void setTotalEntradas(Integer totalEntradas) {
        this.totalEntradas = totalEntradas;
    }

    public Integer getTotalSalidas() {
        return totalSalidas;
    }

    public void setTotalSalidas(Integer totalSalidas) {
        this.totalSalidas = totalSalidas;
    }

    public Integer getTotalPuntuales() {
        return totalPuntuales;
    }

    public void setTotalPuntuales(Integer totalPuntuales) {
        this.totalPuntuales = totalPuntuales;
    }

    public Integer getTotalRetardos() {
        return totalRetardos;
    }

    public void setTotalRetardos(Integer totalRetardos) {
        this.totalRetardos = totalRetardos;
    }

    public Integer getTotalAnticipados() {
        return totalAnticipados;
    }

    public void setTotalAnticipados(Integer totalAnticipados) {
        this.totalAnticipados = totalAnticipados;
    }

    public List<RegistroDTO> getRegistros() {
        return registros;
    }

    public void setRegistros(List<RegistroDTO> registros) {
        this.registros = registros;
    }
}
