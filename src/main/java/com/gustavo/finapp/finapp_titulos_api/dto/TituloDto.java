package com.gustavo.finapp.finapp_titulos_api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TituloDto {

    private Long idTitulo;
    private String tipoLado;
    private Long idCliente;
    private Long idFornecedor;
    private Long idTipoTitulo;
    private String numeroDocumento;
    private String descricao;
    private LocalDate dtEmissao;
    private LocalDate dtVencimento;
    private BigDecimal valorOriginal;
    private BigDecimal valorSaldo;
    private String status;
    private LocalDate dtCriacao;
    private LocalDate dtAtualizacao;
    private String situacaoCobranca;
    private Integer diasEmAtraso;

    // construtores:
    public TituloDto() {
    }

    public TituloDto(Long idTitulo,
                     String tipoLado,
                     Long idCliente,
                     Long idFornecedor,
                     Long idTipoTitulo,
                     String numeroDocumento,
                     String descricao,
                     LocalDate dtEmissao,
                     LocalDate dtVencimento,
                     BigDecimal valorOriginal,
                     BigDecimal valorSaldo,
                     String status,
                     LocalDate dtCriacao,
                     LocalDate dtAtualizacao,
                     String situacaoCobranca,
                     Integer diasEmAtraso) {

        this.idTitulo = idTitulo;
        this.tipoLado = tipoLado;
        this.idCliente = idCliente;
        this.idFornecedor = idFornecedor;
        this.idTipoTitulo = idTipoTitulo;
        this.numeroDocumento = numeroDocumento;
        this.descricao = descricao;
        this.dtEmissao = dtEmissao;
        this.dtVencimento = dtVencimento;
        this.valorOriginal = valorOriginal;
        this.valorSaldo = valorSaldo;
        this.status = status;
        this.dtCriacao = dtCriacao;
        this.dtAtualizacao = dtAtualizacao;
        this.situacaoCobranca = situacaoCobranca;
        this.diasEmAtraso = diasEmAtraso;
    }

    public Long getIdTitulo() {
        return idTitulo;
    }

    public void setIdTitulo(Long idTitulo) {
        this.idTitulo = idTitulo;
    }

    public String getTipoLado() {
        return tipoLado;
    }

    public void setTipoLado(String tipoLado) {
        this.tipoLado = tipoLado;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Long idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public Long getIdTipoTitulo() {
        return idTipoTitulo;
    }

    public void setIdTipoTitulo(Long idTipoTitulo) {
        this.idTipoTitulo = idTipoTitulo;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDtEmissao() {
        return dtEmissao;
    }

    public void setDtEmissao(LocalDate dtEmissao) {
        this.dtEmissao = dtEmissao;
    }

    public LocalDate getDtVencimento() {
        return dtVencimento;
    }

    public void setDtVencimento(LocalDate dtVencimento) {
        this.dtVencimento = dtVencimento;
    }

    public BigDecimal getValorOriginal() {
        return valorOriginal;
    }

    public void setValorOriginal(BigDecimal valorOriginal) {
        this.valorOriginal = valorOriginal;
    }

    public BigDecimal getValorSaldo() {
        return valorSaldo;
    }

    public void setValorSaldo(BigDecimal valorSaldo) {
        this.valorSaldo = valorSaldo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDtCriacao() {
        return dtCriacao;
    }

    public void setDtCriacao(LocalDate dtCriacao) {
        this.dtCriacao = dtCriacao;
    }

    public LocalDate getDtAtualizacao() {
        return dtAtualizacao;
    }

    public void setDtAtualizacao(LocalDate dtAtualizacao) {
        this.dtAtualizacao = dtAtualizacao;
    }

    public String getSituacaoCobranca() {
        return situacaoCobranca;
    }

    public void setSituacaoCobranca(String situacaoCobranca) {
        this.situacaoCobranca = situacaoCobranca;
    }

    public Integer getDiasEmAtraso() {
        return diasEmAtraso;
    }

    public void setDiasEmAtraso(Integer diasEmAtraso) {
        this.diasEmAtraso = diasEmAtraso;
    }
}
