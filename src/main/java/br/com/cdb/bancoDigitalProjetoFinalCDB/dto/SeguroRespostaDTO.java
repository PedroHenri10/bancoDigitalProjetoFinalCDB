package br.com.cdb.bancoDigitalProjetoFinalCDB.dto;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TiposSeguro;

public class SeguroRespostaDTO {

    private Long id;
    private String numeroApolice;
    private TiposSeguro tipo;
    private double valor;
    private String dataInicio;
    private Long numeroCartaoCredito;

    public SeguroRespostaDTO() {
    }

    public SeguroRespostaDTO(Long id, String numeroApolice, TiposSeguro tipo, double valor, String dataInicio, Long numeroCartaoCredito) {
        this.id = id;
        this.numeroApolice = numeroApolice;
        this.tipo = tipo;
        this.valor = valor;
        this.dataInicio = dataInicio;
        this.numeroCartaoCredito = numeroCartaoCredito;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNumeroApolice() { return numeroApolice; }
    public void setNumeroApolice(String numeroApolice) { this.numeroApolice = numeroApolice; }
    public TiposSeguro getTipo() { return tipo; }
    public void setTipo(TiposSeguro tipo) { this.tipo = tipo; }
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
    public String getDataInicio() { return dataInicio; }
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }
    public Long getNumeroCartaoCredito() { return numeroCartaoCredito; }
    public void setNumeroCartaoCredito(Long numeroCartaoCredito) { this.numeroCartaoCredito = numeroCartaoCredito; }
}
