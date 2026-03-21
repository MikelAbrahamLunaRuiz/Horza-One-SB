package com.example.demo.dto;

import java.util.List;

public class UsuarioCalendarioCompleto {
    private Integer matricula;
    private String nombreCompleto;
    private String correo;
    private List<CalendarioConHorarios> calendarios;

    public UsuarioCalendarioCompleto() {
    }

    public UsuarioCalendarioCompleto(Integer matricula, String nombreCompleto, String correo, List<CalendarioConHorarios> calendarios) {
        this.matricula = matricula;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.calendarios = calendarios;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<CalendarioConHorarios> getCalendarios() {
        return calendarios;
    }

    public void setCalendarios(List<CalendarioConHorarios> calendarios) {
        this.calendarios = calendarios;
    }
}
