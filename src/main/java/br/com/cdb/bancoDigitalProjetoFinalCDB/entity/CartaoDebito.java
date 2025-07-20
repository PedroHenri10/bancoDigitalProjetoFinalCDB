package br.com.cdb.bancoDigitalProjetoFinalCDB.entity;

import com.fasterxml.jackson.annotation.JsonTypeName;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.StatusCartao;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TipoCartao;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.time.LocalDate;

@JsonTypeName("debito")
@Entity
@Table(name = "cartaoDebito")
@PrimaryKeyJoinColumn(name = "numero_cartao")
public class CartaoDebito extends Cartao{
    private int limiteDiario;
    private double valorGastoHoje;
    private LocalDate dataUltimoGasto;

    public CartaoDebito() {
    }

    public CartaoDebito(Long numeroCartao, int senha, StatusCartao status, TipoCartao tipo, Conta conta, Cliente cliente, double faturaAtual, int limiteDiario, double valorGastoHoje, LocalDate dataUltimoGasto) {
        super(numeroCartao, senha, status, tipo, conta, cliente, faturaAtual);
        this.limiteDiario = limiteDiario;
        this.valorGastoHoje = valorGastoHoje;
        this.dataUltimoGasto = dataUltimoGasto;
    }

    public int getLimiteDiario() {
        return limiteDiario;
    }

    public void setLimiteDiario(int limiteDiario) {
        this.limiteDiario = limiteDiario;
    }

    public double getValorGastoHoje(){ return valorGastoHoje; }

    public void setValorGastoHoje(double valorGastoHoje){ this.valorGastoHoje = valorGastoHoje; }

    public LocalDate getDataUltimoGasto(){ return dataUltimoGasto; }

    public void setDataUltimoGasto(LocalDate dataUltimoGasto){ this.dataUltimoGasto = dataUltimoGasto; }
}
