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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigDecimal limiteCredito;
    private double gastosMensais;
    private double taxaUtilizacao;
    private TiposSeguro seguro;

    @OneToOne(mappedBy = "cartaoCredito", cascade = CascadeType.ALL)
    private Seguro seguros;

    public Seguro getSeguros() {
        return seguros;
    }

    public void setSeguros(Seguro seguros) {
        this.seguros = seguros;
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
        return seguro;
    }

    public void setSeguro(TiposSeguro seguro) {
        this.seguro = seguro;
    }

    public CartaoCredito(){
        super();
    }

    public CartaoCredito(int numeroCartao, int senha, boolean ativo, StatusCartao status, TipoCartao tipo, Conta conta, double faturaAtual, BigDecimal limiteCredito, double gastosMensais, double taxaUtilizacao, TiposSeguro seguro, Seguro seguros) {
        super(numeroCartao, senha, ativo, status, tipo, conta, faturaAtual);
        this.limiteCredito = limiteCredito;
        this.gastosMensais = gastosMensais;
        this.taxaUtilizacao = taxaUtilizacao;
        this.seguro = seguro;
        this.seguros = seguros;
    }
}
