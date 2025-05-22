package br.com.cdb.bancoDigitalProjetoFinalCDB.entity;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.StatusCartao;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TipoCartao;
import jakarta.persistence.*;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TiposSeguro;

import java.math.BigDecimal;

@Entity
@Table(name = "cartaoCredito")
@PrimaryKeyJoinColumn(name = "numero_cartao")
public class CartaoCredito extends Cartao{

    private BigDecimal limiteCredito;
    private double gastosMensais;
    private double taxaUtilizacao;
    private TiposSeguro tipoSeguro;

    @OneToOne(mappedBy = "cartaoCredito", cascade = CascadeType.ALL)
    private Seguro seguro;

    public Seguro getSeguros() {
        return seguro;
    }

    public void setSeguros(Seguro seguro) {
        this.seguro = seguro;
    }

    public BigDecimal getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(BigDecimal limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public double getGastosMensais() {
        return gastosMensais;
    }

    public void setGastosMensais(double gastosMensais) {
        this.gastosMensais = gastosMensais;
    }

    public double getTaxaUtilizacao() {
        return taxaUtilizacao;
    }

    public void setTaxaUtilizacao(double taxaUtilizacao) {
        this.taxaUtilizacao = taxaUtilizacao;
    }

    public TiposSeguro getSeguro() {
        return tipoSeguro;
    }

    public void setSeguro(TiposSeguro seguro) {
        this.tipoSeguro = tipoSeguro;
    }

    public CartaoCredito(){
    }

    public CartaoCredito(Long numeroCartao, int senha, boolean ativo, StatusCartao status, TipoCartao tipo, Conta conta, Cliente cliente, double faturaAtual, BigDecimal limiteCredito, double gastosMensais, double taxaUtilizacao, TiposSeguro tipoSeguro, Seguro seguro) {
        super(numeroCartao, senha, ativo, status, tipo, conta, cliente, faturaAtual);
        this.limiteCredito = limiteCredito;
        this.gastosMensais = gastosMensais;
        this.taxaUtilizacao = taxaUtilizacao;
        this.tipoSeguro = tipoSeguro;
        this.seguro = seguro;
    }
}
