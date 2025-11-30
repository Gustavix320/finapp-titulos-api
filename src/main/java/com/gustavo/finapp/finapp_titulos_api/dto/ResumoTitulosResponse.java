package com.gustavo.finapp.finapp_titulos_api.dto;

import java.math.BigDecimal;

public class ResumoTitulosResponse {

    private long quantidadeTitulos;
    private BigDecimal totalGeral;
    private BigDecimal totalReceber;
    private BigDecimal totalPagar;
    private BigDecimal totalVencido;
    private BigDecimal totalHoje;
    private BigDecimal totalAVencer;

    public ResumoTitulosResponse() {
    }

    public ResumoTitulosResponse(long quantidadeTitulos,
                                 BigDecimal totalGeral,
                                 BigDecimal totalReceber,
                                 BigDecimal totalPagar,
                                 BigDecimal totalVencido,
                                 BigDecimal totalHoje,
                                 BigDecimal totalAVencer) {
        this.quantidadeTitulos = quantidadeTitulos;
        this.totalGeral = totalGeral;
        this.totalReceber = totalReceber;
        this.totalPagar = totalPagar;
        this.totalVencido = totalVencido;
        this.totalHoje = totalHoje;
        this.totalAVencer = totalAVencer;
    }

    public long getQuantidadeTitulos() {
        return quantidadeTitulos;
    }

    public void setQuantidadeTitulos(long quantidadeTitulos) {
        this.quantidadeTitulos = quantidadeTitulos;
    }

    public BigDecimal getTotalGeral() {
        return totalGeral;
    }

    public void setTotalGeral(BigDecimal totalGeral) {
        this.totalGeral = totalGeral;
    }

    public BigDecimal getTotalReceber() {
        return totalReceber;
    }

    public void setTotalReceber(BigDecimal totalReceber) {
        this.totalReceber = totalReceber;
    }

    public BigDecimal getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(BigDecimal totalPagar) {
        this.totalPagar = totalPagar;
    }

    public BigDecimal getTotalVencido() {
        return totalVencido;
    }

    public void setTotalVencido(BigDecimal totalVencido) {
        this.totalVencido = totalVencido;
    }

    public BigDecimal getTotalHoje() {
        return totalHoje;
    }

    public void setTotalHoje(BigDecimal totalHoje) {
        this.totalHoje = totalHoje;
    }

    public BigDecimal getTotalAVencer() {
        return totalAVencer;
    }

    public void setTotalAVencer(BigDecimal totalAVencer) {
        this.totalAVencer = totalAVencer;
    }
}
