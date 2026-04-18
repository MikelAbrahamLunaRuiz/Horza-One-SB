package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequest {
    private String correo;
    
    @JsonProperty("contrasenia") // Acepta "contrasenia" del JSON y lo mapea a "contrasena"
    private String contrasena;

    // Constructor sin argumentos
    public LoginRequest() {
    }

    // Constructor con todos los argumentos
    public LoginRequest(String correo, String contrasena) {
        this.correo = correo;
        this.contrasena = contrasena;
    }

    // Getters y Setters
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
