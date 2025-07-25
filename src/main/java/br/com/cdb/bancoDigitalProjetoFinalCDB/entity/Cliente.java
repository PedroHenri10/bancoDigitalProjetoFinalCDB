package br.com.cdb.bancoDigitalProjetoFinalCDB.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TipoCliente;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TipoCliente;

@Entity
@Table(name = "clientes")
@JsonIdentityInfo(
	    generator = PropertyGenerator.class,
	    property = "id",
	    scope = Cliente.class
	)
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true, nullable = false, length =11)
    private String cpf;

    @Enumerated(EnumType.STRING)
    private TipoCliente tipoCliente;

    private LocalDate dataNasc;
    private String rua;
    private int numero;
    private String estado;
    private String cidade;
    private String cep;

    private String senha;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Conta> contas;
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cartao> cartoes;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public String getRua() {
        return rua;
    }

    public int getNumero() {
        return numero;
    }

    public String getEstado() {
        return estado;
    }

    public String getCidade() {
        return cidade;
    }

    public String getCep() {
        return cep;
    }

    public String getSenha() {
        return senha;
    }

    public List<Conta> getContas() {
        return contas;
    }
    
    public List<Cartao> getCartoes() {
        return cartoes;
    }

    public void setCartoes(List<Cartao> cartoes) {
        this.cartoes = cartoes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setContas(List<Conta> contas) {
        this.contas = contas;
    }
    
    public Cliente() {
    }

    public Cliente(Long id, String nome, String cpf, TipoCliente tipoCliente, LocalDate dataNasc, String rua, int numero, String estado, String cidade, String cep, String senha, List<Conta> contas, List<Cartao> cartoes) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.tipoCliente = tipoCliente;
        this.dataNasc = dataNasc;
        this.rua = rua;
        this.numero = numero;
        this.estado = estado;
        this.cidade = cidade;
        this.cep = cep;
        this.senha = senha;
        this.contas = contas;
        this.cartoes = cartoes;
    }
}
