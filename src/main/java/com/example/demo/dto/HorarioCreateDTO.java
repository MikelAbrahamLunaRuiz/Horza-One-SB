package com.example.demo.dto;

/**
 * DTO para crear un Horario
 */
public class HorarioCreateDTO {
    
    private Integer idCalendario;
    private String diaSemana; // "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"
    private String nombreHorario;
    private String descripcion;
    private String activoHorario = "Activo";  // Por defecto Activo

    public HorarioCreateDTO() {
    }

    public HorarioCreateDTO(Integer idCalendario, String diaSemana, String nombreHorario, 
                            String descripcion, String activoHorario) {
        this.idCalendario = idCalendario;
        this.diaSemana = diaSemana;
        this.nombreHorario = nombreHorario;
        this.descripcion = descripcion;
        this.activoHorario = activoHorario;
    }

    // Getters y Setters
    public Integer getIdCalendario() {
        return idCalendario;
    }

    public void setIdCalendario(Integer idCalendario) {
        this.idCalendario = idCalendario;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getNombreHorario() {
        return nombreHorario;
    }

    public void setNombreHorario(String nombreHorario) {
        this.nombreHorario = nombreHorario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getActivoHorario() {
        return activoHorario;
    }

    public void setActivoHorario(String activoHorario) {
        this.activoHorario = activoHorario;
    }
}
