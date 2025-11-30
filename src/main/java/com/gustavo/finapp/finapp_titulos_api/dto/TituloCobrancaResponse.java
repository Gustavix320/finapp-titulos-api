package com.gustavo.finapp.finapp_titulos_api.dto;

import java.util.List;

public class TituloCobrancaResponse {

    private List<TituloDto> vencidos;
    private List<TituloDto> vencimentoHoje;
    private List<TituloDto> aVencer;

    public TituloCobrancaResponse() {
    }

    public TituloCobrancaResponse(List<TituloDto> vencidos,
                                  List<TituloDto> vencimentoHoje,
                                  List<TituloDto> aVencer) {
        this.vencidos = vencidos;
        this.vencimentoHoje = vencimentoHoje;
        this.aVencer = aVencer;
    }

    public List<TituloDto> getVencidos() {
        return vencidos;
    }

    public void setVencidos(List<TituloDto> vencidos) {
        this.vencidos = vencidos;
    }

    public List<TituloDto> getVencimentoHoje() {
        return vencimentoHoje;
    }

    public void setVencimentoHoje(List<TituloDto> vencimentoHoje) {
        this.vencimentoHoje = vencimentoHoje;
    }

    public List<TituloDto> getAVencer() {
        return aVencer;
    }

    public void setAVencer(List<TituloDto> aVencer) {
        this.aVencer = aVencer;
    }
}
