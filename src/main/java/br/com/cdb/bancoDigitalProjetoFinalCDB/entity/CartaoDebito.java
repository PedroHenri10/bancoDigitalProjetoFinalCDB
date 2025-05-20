package br.com.cdb.bancoDigitalProjetoFinalCDB.entity;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.StatusCartao;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TipoCartao;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name = "cartaoDebito")
public class CartaoDebito extends Cartao{
    private int limiteDiario;

    public CartaoDebito(int numeroCartao, int senha, boolean ativo, StatusCartao status, TipoCartao tipo, Conta conta, double faturaAtual, int limiteDiario) {
        super(numeroCartao, senha, ativo, status, tipo, conta, faturaAtual);
        this.limiteDiario = limiteDiario;
    }

    public int getLimiteDiario() {
        return limiteDiario;
    }

    public void setLimiteDiario(int limiteDiario) {
        this.limiteDiario = limiteDiario;
    }
}
