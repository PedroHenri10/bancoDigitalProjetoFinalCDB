package br.com.cdb.bancoDigitalProjetoFinalCDB.entity;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.StatusCartao;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TipoCartao;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "cartao")
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numeroCartao;
    private int senha;
    private boolean ativo;

    @Enumerated(EnumType.STRING)
    private StatusCartao status;

    @Enumerated(EnumType.STRING)
    private TipoCartao tipo;

    @ManyToOne
    @JoinColumn(name = "numero_conta")
    private Conta conta;

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

    public Cartao(int numeroCartao, int senha, boolean ativo, StatusCartao status, TipoCartao tipo) {
        this.numeroCartao = numeroCartao;
        this.senha = senha;
        this.ativo = ativo;
        this.status = status;
        this.tipo = tipo;
    }

}

