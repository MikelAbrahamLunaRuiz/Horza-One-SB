package com.example.demo.dto;

import java.util.List;

public class GrupoIntegrantesRequest {

    private List<Integer> matriculas;

    public GrupoIntegrantesRequest() {
    }

    public List<Integer> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Integer> matriculas) {
        this.matriculas = matriculas;
    }
}
