package com.example.demo.dto;

public class RegistroAccesoResponse {
    private boolean exito;
    private String mensaje;
    private String estadoRegistro; // "Puntual", "Retardo", "Anticipado"
    private Integer minutosDiferencia; // Diferencia en minutos (positivo=retardo, negativo=anticipado)
    private RegistroDTO registro;
    
    // Información del bloque horario (nueva)
    private String nombreBloque;
    private String horaInicio;
    private String horaFin;
    private String nombreArea;

    // Constructor sin argumentos
    public RegistroAccesoResponse() {
    }

    // Constructor con argumentos (original - mantener compatibilidad)
    public RegistroAccesoResponse(boolean exito, String mensaje, String estadoRegistro, Integer minutosDiferencia, RegistroDTO registro) {
        this.exito = exito;
        this.mensaje = mensaje;
        this.estadoRegistro = estadoRegistro;
        this.minutosDiferencia = minutosDiferencia;
        this.registro = registro;
    }
    
    // Constructor extendido con información de bloque
    public RegistroAccesoResponse(boolean exito, String mensaje, String estadoRegistro, Integer minutosDiferencia, 
                                   RegistroDTO registro, String nombreBloque, String horaInicio, String horaFin, String nombreArea) {
        this.exito = exito;
        this.mensaje = mensaje;
        this.estadoRegistro = estadoRegistro;
        this.minutosDiferencia = minutosDiferencia;
        this.registro = registro;
        this.nombreBloque = nombreBloque;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.nombreArea = nombreArea;
    }

    // Getters y Setters
    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(String estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public Integer getMinutosDiferencia() {
        return minutosDiferencia;
    }

    public void setMinutosDiferencia(Integer minutosDiferencia) {
        this.minutosDiferencia = minutosDiferencia;
    }

    public RegistroDTO getRegistro() {
        return registro;
    }

    public void setRegistro(RegistroDTO registro) {
        this.registro = registro;
    }

    public String getNombreBloque() {
        return nombreBloque;
    }

    public void setNombreBloque(String nombreBloque) {
        this.nombreBloque = nombreBloque;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }
}
