package br.com.cdb.bancoDigitalProjetoFinalCDB.service;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Conta;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Transferencia;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.StatusTransferencia;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TipoTransferencia;
import br.com.cdb.bancoDigitalProjetoFinalCDB.exception.ContaNaoEncontradaException;
import br.com.cdb.bancoDigitalProjetoFinalCDB.exception.SaldoInsuficienteException;
import br.com.cdb.bancoDigitalProjetoFinalCDB.repository.ContaRepository;
import br.com.cdb.bancoDigitalProjetoFinalCDB.repository.TransferenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransferenciaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private TransferenciaRepository transferenciaRepository;

    public Transferencia realizarTransferenciaPix(Long contaOrigemId, Long contaDestinoId, BigDecimal valor, String descricao) {
        Conta origem = contaRepository.findById(contaOrigemId)
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta de origem não encontrada."));

        Conta destino = contaRepository.findById(contaDestinoId)
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta de destino não encontrada."));

        if (origem.getSaldo() < valor.doubleValue()) {
            throw new SaldoInsuficienteException("Saldo insuficiente na conta de origem.");
        }

        origem.setSaldo(origem.getSaldo() - valor.doubleValue());
        destino.setSaldo(destino.getSaldo() + valor.doubleValue());

        contaRepository.save(origem);
        contaRepository.save(destino);

        Transferencia transferencia = new Transferencia(valor, descricao, TipoTransferencia.PIX, origem, destino);
        transferencia.setStatus(StatusTransferencia.CONCLUIDA);
        return transferenciaRepository.save(transferencia);
    }

    public List<Transferencia> listarTransferenciasPorConta(Long contaId) {
        return transferenciaRepository.findByContaOrigem_NumeroContaOrContaDestino_NumeroConta(contaId, contaId);
    }

    public Transferencia buscarTransferenciaPorId(Long id) {
        return transferenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transferência não encontrada."));
    }
}
