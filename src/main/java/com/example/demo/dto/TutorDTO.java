package com.example.demo.dto;

public class TutorDTO {

    private Integer idTutor;
    private String nombre;
    private String correo;
    private String contrasenia;

    public TutorDTO() {
    }

    public TutorDTO(Integer idTutor, String nombre, String correo, String contrasenia) {
        this.idTutor = idTutor;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasenia = contrasenia;
    }

    public Integer getIdTutor() {
        return idTutor;
    }

    public void setIdTutor(Integer idTutor) {
        this.idTutor = idTutor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}
