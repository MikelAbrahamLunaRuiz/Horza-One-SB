package com.example.demo.dto;

import java.time.LocalDate;
import java.util.List;

public class EstadisticasMensualDTO {
    private Integer mes;
    private Integer anio;
    private String tipoEstadistica;
    private List<EstadisticaDiaDTO> estadisticasPorDia;

    public EstadisticasMensualDTO() {
    }

    public EstadisticasMensualDTO(Integer mes, Integer anio, String tipoEstadistica, List<EstadisticaDiaDTO> estadisticasPorDia) {
        this.mes = mes;
        this.anio = anio;
        this.tipoEstadistica = tipoEstadistica;
        this.estadisticasPorDia = estadisticasPorDia;
    }

    // Getters y Setters
    public Integer getMes() { return mes; }
    public void setMes(Integer mes) { this.mes = mes; }

    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }

    public String getTipoEstadistica() { return tipoEstadistica; }
    public void setTipoEstadistica(String tipoEstadistica) { this.tipoEstadistica = tipoEstadistica; }

    public List<EstadisticaDiaDTO> getEstadisticasPorDia() { return estadisticasPorDia; }
    public void setEstadisticasPorDia(List<EstadisticaDiaDTO> estadisticasPorDia) { this.estadisticasPorDia = estadisticasPorDia; }

    // Clase interna
    public static class EstadisticaDiaDTO {
        private Integer dia;
        private LocalDate fecha;
        private Integer cantidad;

        public EstadisticaDiaDTO() {
        }

        public EstadisticaDiaDTO(Integer dia, LocalDate fecha, Integer cantidad) {
            this.dia = dia;
            this.fecha = fecha;
            this.cantidad = cantidad;
        }

        public Integer getDia() { return dia; }
        public void setDia(Integer dia) { this.dia = dia; }

        public LocalDate getFecha() { return fecha; }
        public void setFecha(LocalDate fecha) { this.fecha = fecha; }

        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    }
}