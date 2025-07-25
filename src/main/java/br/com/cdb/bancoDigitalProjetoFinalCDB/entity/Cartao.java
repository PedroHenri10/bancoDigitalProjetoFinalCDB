package br.com.cdb.bancoDigitalProjetoFinalCDB.entity;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.StatusCartao;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TipoCartao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
		  use = JsonTypeInfo.Id.NAME,
		  include = JsonTypeInfo.As.PROPERTY,
		  property = "tipoCartao"
		)
@JsonSubTypes({
	  @JsonSubTypes.Type(value = CartaoCredito.class, name = "credito"),
	  @JsonSubTypes.Type(value = CartaoDebito.class, name = "debito")
	})

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_cartao", discriminatorType = DiscriminatorType.STRING)
@Table(name = "cartao")
@JsonIdentityInfo(
	    generator = PropertyGenerator.class,
	    property = "numeroCartao",
	    scope = Cartao.class
	)
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numeroCartao;
    private int senha;
    private double faturaAtual;

    @Enumerated(EnumType.STRING)
    private StatusCartao status;

    @Enumerated(EnumType.STRING)
    private TipoCartao tipo;

    @ManyToOne
    @JoinColumn(name = "numero_conta")
    private Conta conta;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "cartaoCredito", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Seguro> seguros = new ArrayList<>();

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

    public List<Seguro> getSeguros() {
        return seguros;
    }

    public void setSeguros(List<Seguro> seguros) {
        this.seguros = seguros;
    }

    public Cartao() {}

    public Cartao(Long numeroCartao, int senha, StatusCartao status, TipoCartao tipo, Conta conta, Cliente cliente, double faturaAtual) {
        this.numeroCartao = numeroCartao;
        this.senha = senha;
        this.status = status;
        this.tipo = tipo;
        this.conta = conta;
        this.cliente = cliente;
        this.faturaAtual = faturaAtual;
    }

}

