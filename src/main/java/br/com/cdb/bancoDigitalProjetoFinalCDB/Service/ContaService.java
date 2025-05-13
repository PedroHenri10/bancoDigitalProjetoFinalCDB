package br.com.cdb.bancoDigitalProjetoFinalCDB.Service;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.*;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.StatusTransferencia;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TipoCliente;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TipoTransferencia;
import br.com.cdb.bancoDigitalProjetoFinalCDB.exception.DadosInvalidosException;
import br.com.cdb.bancoDigitalProjetoFinalCDB.exception.OperacaoNaoPermitidaException;
import br.com.cdb.bancoDigitalProjetoFinalCDB.exception.SaldoInsuficienteException;
import br.com.cdb.bancoDigitalProjetoFinalCDB.repository.ClienteRepository;
import br.com.cdb.bancoDigitalProjetoFinalCDB.repository.ContaRepository;
import br.com.cdb.bancoDigitalProjetoFinalCDB.repository.TransferenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TransferenciaRepository transferenciaRepository;

    public ContaCorrente criarContaCorrente(ContaCorrente conta) {
        validarClienteExiste(conta.getCliente().getId());
        conta.setSaldo(0.0);
        return contaRepository.save(conta);
    }

    public ContaPoupanca criarContaPoupanca(ContaPoupanca conta) {
        validarClienteExiste(conta.getCliente().getId());
        conta.setSaldo(0.0);
        return contaRepository.save(conta);
    }

    public List<Conta> listarTodasContas() {
        return contaRepository.findAll();
    }

    public Conta obterDadosConta(Long id) {
        return buscarContaPorId(id);
    }

    public double consultarSaldo(Long contaId) {
        return buscarContaPorId(contaId).getSaldo();
    }


    public boolean contaExiste(Long contaId) {
        return contaRepository.existsById(contaId);
    }

    public boolean clienteExiste(Long clienteId) {
        return clienteRepository.existsById(clienteId);
    }


    public boolean verificarTipoConta(Long contaId, Class<?> tipoClasse) {
        Conta conta = buscarContaPorId(contaId);
        return tipoClasse.isInstance(conta);
    }

    public boolean verificarCartoesVinculados(Long contaId) {
        Conta conta = buscarContaPorId(contaId);
        return conta.getCartoes() != null && !conta.getCartoes().isEmpty();
    }

    private void validarClienteExiste(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new DadosInvalidosException("Cliente não encontrado.");
        }
    }
}
