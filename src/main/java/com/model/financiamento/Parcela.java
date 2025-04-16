package com.model.financiamento;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Parcela {

    private Integer id;
    private int numeroParcela;
    private String fgPago;
    private Double juros;
    private Double amortizacao;
    private LocalDate dtVencimento;
    private LocalDate dtPagamento;
    private Integer idContrato;

    public Parcela(Integer id, int numeroParcela, String fgPago, Double juros, Double amortizacao, LocalDate dtVencimento, LocalDate dtPagamento, Integer idContrato) {
        this.id = id;
        this.numeroParcela = numeroParcela;
        this.fgPago = fgPago;
        this.juros = juros;
        this.amortizacao = amortizacao;
        this.dtVencimento = dtVencimento;
        this.dtPagamento = dtPagamento;
        this.idContrato = idContrato;
    }

    public Integer getId() {
        return id;
    }

    public int getNumeroParcela() {
        return numeroParcela;
    }

    public String getFgPago() {
        return fgPago;
    }

    public Double getJuros() {
        return juros;
    }

    public Double getAmortizacao() {
        return amortizacao;
    }

    public LocalDate getDtVencimento() {
        return dtVencimento;
    }

    public LocalDate getDtPagamento() {
        return dtPagamento;
    }

    public Integer getIdContrato() {
        return idContrato;
    }

    @Override
    public String toString() {
        return "Parcela{" +
                "id=" + id +
                ", numeroParcela=" + numeroParcela +
                ", fgPago='" + fgPago + '\'' +
                ", juros=" + juros +
                ", amortizacao=" + amortizacao +
                ", dtVencimento=" + dtVencimento +
                ", dtPagamento=" + dtPagamento +
                ", idContrato=" + idContrato +
                '}';
    }

    public String toString2() {
        return "{" +
                "id=" + id +
                ", numeroParcela=" + numeroParcela +
                ", dtPagamento=" + dtPagamento +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Parcela parcela = (Parcela) o;
        return numeroParcela < parcela.numeroParcela
                && Objects.equals(fgPago, parcela.fgPago)
//                && dtVencimento.isBefore(parcela.dtVencimento)
                && dtPagamento.isAfter(parcela.dtPagamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroParcela, fgPago, dtVencimento, dtPagamento);
    }
}
