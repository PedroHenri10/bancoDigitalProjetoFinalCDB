package br.com.cdb.bancoDigitalProjetoFinalCDB.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "contaPoupanca")
public class ContaPoupanca extends Conta{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private double taxaRendimentoAnual;

    public double getTaxaRendimentoAnual() {
        return taxaRendimentoAnual;
    }

    public void setTaxaRendimentoAnual(double taxaRendimentoAnual) {
        this.taxaRendimentoAnual = taxaRendimentoAnual;
    }

    public ContaPoupanca(long numeroConta, double saldo, Cliente cliente, double taxaRendimentoAnual) {
        super(numeroConta, saldo, cliente);
        this.taxaRendimentoAnual = taxaRendimentoAnual;
    }
}
