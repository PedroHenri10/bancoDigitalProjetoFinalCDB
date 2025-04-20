package br.com.cdb.bancoDigitalProjetoFinalCDB.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "contaCorrente")
public class ContaCorrente extends Conta{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private double taxaManutencaoMensal;

    public double getTaxaManutencaoMensal() {
        return taxaManutencaoMensal;
    }

    public void setTaxaManutencaoMensal(double taxaManutencaoMensal) {
        this.taxaManutencaoMensal = taxaManutencaoMensal;
    }

    public ContaCorrente(long numeroConta, double saldo, Cliente cliente, double taxaManutencaoMensal) {
        super(numeroConta, saldo, cliente);
        this.taxaManutencaoMensal = taxaManutencaoMensal;
    }
}
