package com.example.demo.dto;

import java.util.List;

/**
 * DTO para representar un HORARIO completo con sus 7 días y bloques
 * Estructura: HORARIO → DIA_HORARIO → BLOQUES_HORARIO
 * NO incluye información de calendario (eso está en CalendarioConHorarios)
 */
public class HorarioConBloques {
    private Integer idHorario;
    private String nombreHorario;
    private String descripcion;
    private String activoHorario;
    private List<DiaConBloques> dias; // Lista de los 7 días con sus bloques

    // Clase interna para representar cada día con sus bloques
    public static class DiaConBloques {
        private Integer idDia;
        private String nombreDia;
        private Integer ordenDia;
        private List<BloqueHorarioDTO> bloques;

        public DiaConBloques() {
        }

        public DiaConBloques(Integer idDia, String nombreDia, Integer ordenDia, List<BloqueHorarioDTO> bloques) {
            this.idDia = idDia;
            this.nombreDia = nombreDia;
            this.ordenDia = ordenDia;
            this.bloques = bloques;
        }

        public Integer getIdDia() {
            return idDia;
        }

        public void setIdDia(Integer idDia) {
            this.idDia = idDia;
        }

        public String getNombreDia() {
            return nombreDia;
        }

        public void setNombreDia(String nombreDia) {
            this.nombreDia = nombreDia;
        }

        public Integer getOrdenDia() {
            return ordenDia;
        }

        public void setOrdenDia(Integer ordenDia) {
            this.ordenDia = ordenDia;
        }

        public List<BloqueHorarioDTO> getBloques() {
            return bloques;
        }

        public void setBloques(List<BloqueHorarioDTO> bloques) {
            this.bloques = bloques;
        }
    }

    public HorarioConBloques() {
    }

    public HorarioConBloques(Integer idHorario, String nombreHorario, String descripcion, 
                             String activoHorario, List<DiaConBloques> dias) {
        this.idHorario = idHorario;
        this.nombreHorario = nombreHorario;
        this.descripcion = descripcion;
        this.activoHorario = activoHorario;
        this.dias = dias;
    }

    public Integer getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Integer idHorario) {
        this.idHorario = idHorario;
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

    public List<DiaConBloques> getDias() {
        return dias;
    }

    public void setDias(List<DiaConBloques> dias) {
        this.dias = dias;
    }
}
