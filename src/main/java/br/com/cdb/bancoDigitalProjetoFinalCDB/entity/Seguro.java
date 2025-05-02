package br.com.cdb.bancoDigitalProjetoFinalCDB.entity;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TiposSeguro;
import jakarta.persistence.*;

@Entity
@Table(name = "seguro")
public class Seguro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroApolice;

    @Enumerated(EnumType.STRING)
    private TiposSeguro tipo;

    private double valor;

    private String dataInicio;

    @OneToOne
    @JoinColumn(name = "cartao_credito_id")
    private CartaoCredito cartaoCredito;

    public Long getId() {
        return id;
    }

    public String getNumeroApolice() {
        return numeroApolice;
    }

    public void setNumeroApolice(String numeroApolice) {
        this.numeroApolice = numeroApolice;
    }

    public TiposSeguro getTipo() {
        return tipo;
    }

    public void setTipo(TiposSeguro tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public CartaoCredito getCartaoCredito() {
        return cartaoCredito;
    }

    public void setCartaoCredito(CartaoCredito cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
    }

    public Seguro() {
    }

    public Seguro(Long id, String numeroApolice, TiposSeguro tipo, double valor, String dataInicio, CartaoCredito cartaoCredito) {
        this.id = id;
        this.numeroApolice = numeroApolice;
        this.tipo = tipo;
        this.valor = valor;
        this.dataInicio = dataInicio;
        this.cartaoCredito = cartaoCredito;
    }
}

