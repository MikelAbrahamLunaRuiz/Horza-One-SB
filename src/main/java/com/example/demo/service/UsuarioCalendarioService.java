package com.example.demo.service;

import java.time.LocalDate;

import com.example.demo.dto.CalendarioConHorarios;
import com.example.demo.dto.UsuarioCalendarioCompleto;

public interface UsuarioCalendarioService {
    UsuarioCalendarioCompleto obtenerCalendariosCompletos(Integer matricula);
    void asignarCalendario(Integer matricula, Integer idCalendario);
    CalendarioConHorarios obtenerCalendarioActivoEnFecha(Integer matricula, LocalDate fecha);
    void desasignarCalendario(Integer matricula, Integer idCalendario);
}
