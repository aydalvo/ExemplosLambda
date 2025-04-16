package com.model.financiamento;

import java.util.List;

public class Contrato {

    private Integer id;
    private String numeroContrato;
    private List<Parcela> parcelas;
    private Double vlFinanciado;
    private Double txJuros;
    private Double vlFuturo;
    private int numeroParcelas;

    public Contrato(Integer id, String numeroContrato, List<Parcela> parcelas, Double vlFinanciado, Double txJuros, Double vlFuturo, int numeroParcelas) {
        this.id = id;
        this.numeroContrato = numeroContrato;
        this.parcelas = parcelas;
        this.vlFinanciado = vlFinanciado;
        this.txJuros = txJuros;
        this.vlFuturo = vlFuturo;
        this.numeroParcelas=numeroParcelas;
    }

    public Integer getId() {
        return id;
    }

    public String getNumeroContrato() {
        return numeroContrato;
    }

    public List<Parcela> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<Parcela> parcelas) {
        this.parcelas = parcelas;
    }

    public Double getVlFinanciado() {
        return vlFinanciado;
    }

    public Double getTxJuros() {
        return txJuros;
    }

    public Double getVlFuturo() {
        return vlFuturo;
    }

    public int getNumeroParcelas() {
        return numeroParcelas;
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "id=" + id +
                ", numeroContrato='" + numeroContrato + '\'' +
                ", parcelas=" + parcelas +
                ", vlFinanciado=" + vlFinanciado +
                ", txJuros=" + txJuros +
                ", vlFuturo=" + vlFuturo +
                ", numeroParcelas=" + numeroParcelas +
                '}';
    }
}
