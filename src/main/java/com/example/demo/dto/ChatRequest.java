package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChatRequest {

    @NotBlank(message = "La pregunta no puede estar vacía")
    @Size(max = 500, message = "La pregunta no puede superar 500 caracteres")
    private String pregunta;

    public ChatRequest() {}

    public String getPregunta() { return pregunta; }
    public void setPregunta(String pregunta) { this.pregunta = pregunta; }
}
