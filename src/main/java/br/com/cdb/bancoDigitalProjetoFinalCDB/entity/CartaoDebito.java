package br.com.cdb.bancoDigitalProjetoFinalCDB.entity;

import com.fasterxml.jackson.annotation.JsonTypeName;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.StatusCartao;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TipoCartao;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@JsonTypeName("debito")
@Entity
@Table(name = "cartaoDebito")
@PrimaryKeyJoinColumn(name = "numero_cartao")
public class CartaoDebito extends Cartao{
    private int limiteDiario;
    
    public CartaoDebito() {
    }

    public CartaoDebito(Long numeroCartao, int senha, StatusCartao status, TipoCartao tipo, Conta conta, Cliente cliente, double faturaAtual, int limiteDiario) {
        super(numeroCartao, senha, status, tipo, conta, cliente, faturaAtual);
        this.limiteDiario = limiteDiario;
    }

    public int getLimiteDiario() {
        return limiteDiario;
    }

    public void setLimiteDiario(int limiteDiario) {
        this.limiteDiario = limiteDiario;
    }
}
