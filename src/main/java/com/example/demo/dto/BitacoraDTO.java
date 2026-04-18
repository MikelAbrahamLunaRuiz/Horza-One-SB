package com.example.demo.dto;

public class BitacoraDTO {
    private Integer idBitacora;
    private Integer matricula;
    private Integer numEntradas;
    private Integer numInasistencias;
    private Integer numRetardos;
    private Integer numEntradasAnticipadas;

    public BitacoraDTO() {
    }

    public BitacoraDTO(Integer idBitacora, Integer matricula, Integer numEntradas, Integer numInasistencias, Integer numRetardos, Integer numEntradasAnticipadas) {
        this.idBitacora = idBitacora;
        this.matricula = matricula;
        this.numEntradas = numEntradas;
        this.numInasistencias = numInasistencias;
        this.numRetardos = numRetardos;
        this.numEntradasAnticipadas = numEntradasAnticipadas;
    }

    public Integer getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(Integer idBitacora) {
        this.idBitacora = idBitacora;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public Integer getNumEntradas() {
        return numEntradas;
    }

    public void setNumEntradas(Integer numEntradas) {
        this.numEntradas = numEntradas;
    }

    public Integer getNumInasistencias() {
        return numInasistencias;
    }

    public void setNumInasistencias(Integer numInasistencias) {
        this.numInasistencias = numInasistencias;
    }

    public Integer getNumRetardos() {
        return numRetardos;
    }

    public void setNumRetardos(Integer numRetardos) {
        this.numRetardos = numRetardos;
    }

    public Integer getNumEntradasAnticipadas() {
        return numEntradasAnticipadas;
    }

    public void setNumEntradasAnticipadas(Integer numEntradasAnticipadas) {
        this.numEntradasAnticipadas = numEntradasAnticipadas;
    }
}
