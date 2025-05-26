package br.com.cdb.bancoDigitalProjetoFinalCDB.service;

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
import java.math.RoundingMode;
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
        Cliente cliente = clienteRepository.findById(conta.getCliente().getId())
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        conta.setSaldo(0.0);

        conta.setCliente(cliente);
        cliente.getContas().add(conta);

        return contaRepository.save(conta);
    }


    public ContaPoupanca criarContaPoupanca(ContaPoupanca conta) {
        Cliente cliente = clienteRepository.findById(conta.getCliente().getId())
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        conta.setSaldo(0.0);
        conta.setCliente(cliente);
        cliente.getContas().add(conta);

        return contaRepository.save(conta);
    }
    
    public Conta buscarContaPorId(Long id) {
        return contaRepository.findById(id)
                .orElseThrow(() -> new DadosInvalidosException("Conta com ID " + id + " não encontrada."));
    }

    public List<Conta> listarTodasContas() {
        return contaRepository.findAll();
    }

    public Conta obterDadosConta(Long id) {
        return buscarContaPorId(id);
    }

    public void removerConta(Long id) {
        Conta conta = buscarContaPorId(id);
        if (conta.getSaldo() > 0) {
            throw new OperacaoNaoPermitidaException("Conta possui saldo e não pode ser removida.");
        }
        contaRepository.delete(conta);
    }

    public void realizarTransferencia(Long origemId, Long destinoId, BigDecimal valor) {
        Conta origem = buscarContaPorId(origemId);
        Conta destino = buscarContaPorId(destinoId);

        if (origem.getSaldo() < valor.doubleValue()) {
            throw new SaldoInsuficienteException("Saldo insuficiente para transferência.");
        }

        origem.setSaldo(origem.getSaldo() - valor.doubleValue());
        destino.setSaldo(destino.getSaldo() + valor.doubleValue());

        Transferencia transferencia = new Transferencia(valor, "Transferência PIX",
                TipoTransferencia.PIX, origem, destino);
        transferencia.setStatus(StatusTransferencia.CONCLUIDA);

        transferenciaRepository.save(transferencia);
        contaRepository.save(origem);
        contaRepository.save(destino);
    }

    public double consultarSaldo(Long contaId) {
        return buscarContaPorId(contaId).getSaldo();
    }

    public void realizarDeposito(Long contaId, double valor) {
        Conta conta = buscarContaPorId(contaId);
        if (valor <= 0) throw new DadosInvalidosException("Valor de depósito deve ser positivo.");
        conta.setSaldo(conta.getSaldo() + valor);
        atualizarCategoriaCliente(conta.getCliente());
        contaRepository.save(conta);
    }

    public void realizarSaque(Long contaId, double valor) {
        Conta conta = buscarContaPorId(contaId);
        if (valor <= 0) throw new DadosInvalidosException("Valor de saque deve ser positivo.");
        if (conta.getSaldo() < valor) {
            throw new SaldoInsuficienteException("Saldo insuficiente para saque.");
        }
        conta.setSaldo(conta.getSaldo() - valor);
        contaRepository.save(conta);
    }

    public void aplicarTaxaManutencaoMensal(Long contaId) {
        Conta conta = buscarContaPorId(contaId);
        if (conta instanceof ContaCorrente) {
            ContaCorrente cc = (ContaCorrente) conta;
            TipoCliente tipo = conta.getCliente().getTipoCliente();

            double taxa = switch (tipo) {
                case COMUM -> 12.0;
                case SUPER -> 8.0;
                case PREMIUM -> 0.0;
            };

            conta.setSaldo(conta.getSaldo() - taxa);
            contaRepository.save(conta);
        }
    }

    public void aplicarRendimentoMensal(Long contaId) {
        Conta conta = buscarContaPorId(contaId);
        if (conta instanceof ContaPoupanca) {
            ContaPoupanca cp = (ContaPoupanca) conta;
            TipoCliente tipo = conta.getCliente().getTipoCliente();

            double taxaAnual = switch (tipo) {
                case COMUM -> 0.005;
                case SUPER -> 0.007;
                case PREMIUM -> 0.009;
            };

            double taxaMensal = Math.pow(1 + taxaAnual, 1.0 / 12.0) - 1;
            double rendimento = conta.getSaldo() * taxaMensal;

            BigDecimal novoSaldo = BigDecimal.valueOf(conta.getSaldo() + rendimento).setScale(2, RoundingMode.HALF_EVEN);
            conta.setSaldo(novoSaldo.doubleValue());
            contaRepository.save(conta);
        }
    }

    public boolean contaExiste(Long contaId) {
        return contaRepository.existsById(contaId);
    }

    public boolean clienteExiste(Long clienteId) {
        return clienteRepository.existsById(clienteId);
    }

    public void atualizarCategoriaCliente(Cliente cliente) {
        double saldoTotal = cliente.getContas()
                .stream()
                .mapToDouble(Conta::getSaldo)
                .sum();

        if (saldoTotal >= 10000) {
            cliente.setTipoCliente(TipoCliente.PREMIUM);
        } else if (saldoTotal >= 5000) {
            cliente.setTipoCliente(TipoCliente.SUPER);
        } else {
            cliente.setTipoCliente(TipoCliente.COMUM);
        }

        clienteRepository.save(cliente);
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
