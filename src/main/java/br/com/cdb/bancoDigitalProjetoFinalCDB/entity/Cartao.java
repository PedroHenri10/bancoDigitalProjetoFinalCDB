package br.com.cdb.bancoDigitalProjetoFinalCDB.entity;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.StatusCartao;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TipoCartao;
import jakarta.persistence.*;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
		  use = JsonTypeInfo.Id.NAME,
		  include = JsonTypeInfo.As.PROPERTY,
		  property = "tipoCartao"
		)
		@JsonSubTypes({
		  @JsonSubTypes.Type(value = ContaCorrente.class, name = "credito"),
		  @JsonSubTypes.Type(value = ContaPoupanca.class, name = "debito")
		})

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_cartao", discriminatorType = DiscriminatorType.STRING)
@Table(name = "cartao")
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numeroCartao;
    private int senha;
    private boolean ativo;
    private double faturaAtual;

    @Enumerated(EnumType.STRING)
    private StatusCartao status;

    @Enumerated(EnumType.STRING)
    private TipoCartao tipo;

    @ManyToOne
    @JoinColumn(name = "numero_conta")
    @JsonBackReference(value = "conta-cartoes")
    private Conta conta;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonBackReference(value = "cliente-cartoes")
    private Cliente cliente;

    public Long getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(Long numeroCartao) {
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
    
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setFaturaAtual(double faturaAtual){
        this.faturaAtual = faturaAtual;
    }

    public Cartao() {}

    public Cartao(Long numeroCartao, int senha, boolean ativo, StatusCartao status, TipoCartao tipo, Conta conta, Cliente cliente, double faturaAtual) {
        this.numeroCartao = numeroCartao;
        this.senha = senha;
        this.ativo = ativo;
        this.status = status;
        this.tipo = tipo;
        this.conta = conta;
        this.cliente = cliente;
        this.faturaAtual = faturaAtual;
    }

}

