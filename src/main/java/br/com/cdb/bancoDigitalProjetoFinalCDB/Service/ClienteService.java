package br.com.cdb.bancoDigitalProjetoFinalCDB.service;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Cliente;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TipoCliente;
import br.com.cdb.bancoDigitalProjetoFinalCDB.exception.ClienteNaoEncontradoException;
import br.com.cdb.bancoDigitalProjetoFinalCDB.exception.DadosInvalidosException;
import br.com.cdb.bancoDigitalProjetoFinalCDB.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public void validarCpf(String cpf) {
        if (cpf == null || !Pattern.matches("\\d{11}", cpf) || !validarCPFAlgoritmo(cpf)) {
            throw new DadosInvalidosException("CPF inválido.");
        }
    }

    public void validarNome(String nome) {
        if (nome == null || nome.length() < 2 || nome.length() > 100 || !nome.matches("[A-Za-zÀ-ÿ ]+")) {
            throw new DadosInvalidosException("Nome inválido. Apenas letras e espaços (2 a 100 caracteres).");
        }
    }

    public void validarIdade(LocalDate dataNascimento) {
        if (dataNascimento == null) {
            throw new DadosInvalidosException("Data de nascimento não informada.");
        }
        int idade = Period.between(dataNascimento, LocalDate.now()).getYears();
        if (idade < 18) {
            throw new DadosInvalidosException("Cliente deve ter pelo menos 18 anos.");
        }
    }

    public void validarEndereco(String endereco) {
        if (endereco == null || endereco.isBlank()) {
            throw new DadosInvalidosException("Endereço não pode ser vazio.");
        }
        if (!endereco.matches(".*\\d+.*") || !endereco.matches(".*\\d{5}-\\d{3}.*")) {
            throw new DadosInvalidosException("Endereço deve conter número e CEP no formato 00000-000.");
        }
    }
  
    private boolean validarCPFAlgoritmo(String cpf) {
        if (cpf.chars().distinct().count() == 1) return false; // evita CPFs com todos os dígitos iguais

        int soma1 = 0, soma2 = 0;
        for (int i = 0; i < 9; i++) {
            int digito = cpf.charAt(i) - '0';
            soma1 += digito * (10 - i);
            soma2 += digito * (11 - i);
        }
        int verificador1 = 11 - (soma1 % 11);
        if (verificador1 >= 10) verificador1 = 0;
        soma2 += verificador1 * 2;
        int verificador2 = 11 - (soma2 % 11);
        if (verificador2 >= 10) verificador2 = 0;

        return verificador1 == (cpf.charAt(9) - '0') && verificador2 == (cpf.charAt(10) - '0');
    }
}
