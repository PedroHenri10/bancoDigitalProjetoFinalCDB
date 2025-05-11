package br.com.cdb.bancoDigitalProjetoFinalCDB.entity;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.StatusCartao;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TipoCartao;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "cartao")
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numeroCartao;
    private int senha;
    private boolean ativo;
    private double faturaAtual;

    @Enumerated(EnumType.STRING)
    private StatusCartao status;

    @Enumerated(EnumType.STRING)
    private TipoCartao tipo;

    @ManyToOne
    @JoinColumn(name = "numero_conta")
    private Conta conta;

    public int getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(int numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public int getSenha() {
        return senha;
    }

    public void setSenha(int senha) {
        this.senha = senha;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public StatusCartao getStatus() {
        return status;
    }

    public void setStatus(StatusCartao status) {
        this.status = status;
    }

    public TipoCartao getTipo() {
        return tipo;
    }

    public void setTipo(TipoCartao tipo) {
        this.tipo = tipo;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public double getFaturaAtual(){
        return faturaAtual;
    }

    public void setFaturaAtual(double faturaAtual){
        this.faturaAtual = faturaAtual;
    }

    public Cartao() {}

    public Cartao(int numeroCartao, int senha, boolean ativo, StatusCartao status, TipoCartao tipo, Conta conta, double faturaAtual) {
        this.numeroCartao = numeroCartao;
        this.senha = senha;
        this.ativo = ativo;
        this.status = status;
        this.tipo = tipo;
        this.conta = conta;
        this.faturaAtual = faturaAtual;
    }

}

