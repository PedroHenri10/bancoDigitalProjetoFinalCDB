package br.com.cdb.bancoDigitalProjetoFinalCDB.entity;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.StatusCartao;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TipoCartao;
import jakarta.persistence.*;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TiposSeguro;

@Entity
@Table(name = "cartaoCredito")
public class CartaoCredito extends Cartao{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private double LimiteCredito;
    private double gastosMensais;
    private double taxaUtilizacao;
    private TiposSeguro seguro;

    @OneToOne(mappedBy = "cartao", cascade = CascadeType.ALL)
    private Seguro seguros;

    public Seguro getSeguros() {
        return seguros;
    }

    public void setSeguros(Seguro seguros) {
        this.seguros = seguros;
    }

    public double getLimiteCredito() {
        return LimiteCredito;
    }

    public void setLimiteCredito(double limiteCredito) {
        LimiteCredito = limiteCredito;
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

    public CartaoCredito(int numeroCartao, int senha, boolean ativo, StatusCartao status, TipoCartao tipo, double limiteCredito, double gastosMensais, double taxaUtilizacao, TiposSeguro seguro, Seguro seguros) {
        super(numeroCartao, senha, ativo, status, tipo);
        LimiteCredito = limiteCredito;
        this.gastosMensais = gastosMensais;
        this.taxaUtilizacao = taxaUtilizacao;
        this.seguro = seguro;
        this.seguros = seguros;
    }
}
