package br.com.cdb.bancoDigitalProjetoFinalCDB.service;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Cartao;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.CartaoCredito;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Seguro;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TipoCartao;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TiposSeguro;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TipoCliente;
import br.com.cdb.bancoDigitalProjetoFinalCDB.exception.OperacaoNaoPermitidaException;
import br.com.cdb.bancoDigitalProjetoFinalCDB.exception.SeguroNaoEncontradoException;
import br.com.cdb.bancoDigitalProjetoFinalCDB.repository.CartaoRepository;
import br.com.cdb.bancoDigitalProjetoFinalCDB.repository.SeguroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class SeguroService {

    @Autowired
    private SeguroRepository seguroRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    public Seguro contratarSeguroViagem(int cartaoId) {
        Cartao cartao = cartaoRepository.findByNumeroConta(cartaoId);

        if (cartao == null) {
            throw new OperacaoNaoPermitidaException("Cartão não encontrado.");
        }

        if (!(cartao instanceof CartaoCredito credito)) {
            throw new OperacaoNaoPermitidaException("Seguro viagem disponível apenas para cartões de crédito.");
        }

        if (cartao.getConta() == null || cartao.getConta().getCliente() == null) {
            throw new OperacaoNaoPermitidaException("Cartão sem cliente associado.");
        }

        if (seguroRepository.existsByCartaoCreditoAndTipo(TipoCartao.CREDITO, TiposSeguro.SEGURO_VIAGEM)) {
            throw new OperacaoNaoPermitidaException("Este cartão já possui um seguro viagem.");
        }

        TipoCliente tipo = cartao.getConta().getCliente().getTipoCliente();
        double valor = switch (tipo) {
            case PREMIUM -> 0.0;
            case SUPER, COMUM -> 50.0;
        };

        Seguro seguro = new Seguro();
        seguro.setTipo(TiposSeguro.SEGURO_VIAGEM);
        seguro.setValor(valor);
        seguro.setNumeroApolice(UUID.randomUUID().toString());
        seguro.setDataInicio(LocalDate.now().toString());
        seguro.setCartaoCredito(credito);

        return seguroRepository.save(seguro);
    }

    public Seguro contratarSeguroFraude(int cartaoId) {

        Cartao cartao = cartaoRepository.findByNumeroConta(cartaoId);

        if (cartao == null) {
            throw new OperacaoNaoPermitidaException("Cartão não encontrado.");
        }

        if (!(cartao instanceof CartaoCredito credito)) {
            throw new OperacaoNaoPermitidaException("Seguro viagem disponível apenas para cartões de crédito.");
        }

        if (cartao.getConta() == null || cartao.getConta().getCliente() == null) {
            throw new OperacaoNaoPermitidaException("Cartão sem cliente associado.");
        }

        if (seguroRepository.existsByCartaoCreditoAndTipo(TipoCartao.CREDITO, TiposSeguro.SEGURO_VIAGEM)) {
            throw new OperacaoNaoPermitidaException("Este cartão já possui um seguro viagem.");
        }

        Seguro seguro = new Seguro();
        seguro.setTipo(TiposSeguro.SEGURO_FRAUDE);
        seguro.setValor(5000.0);
        seguro.setNumeroApolice(UUID.randomUUID().toString());
        seguro.setDataInicio(LocalDate.now().toString());
        seguro.setCartaoCredito(credito);

        return seguroRepository.save(seguro);
    }

    public Seguro cancelarSeguro(Long id) {
        Seguro seguro = seguroRepository.findById(id)
                .orElseThrow(() -> new SeguroNaoEncontradoException("Seguro não encontrado."));

        if (seguro.getTipo() == TiposSeguro.SEGURO_FRAUDE) {
            throw new OperacaoNaoPermitidaException("Seguro de fraude não pode ser cancelado.");
        }

        seguroRepository.deleteById(id);
        return seguro;
    }

    public Seguro buscarSeguroPorId(Long id) {
        return seguroRepository.findById(id)
                .orElseThrow(() -> new SeguroNaoEncontradoException("Seguro não encontrado."));
    }

    public List<Seguro> listarSegurosPorCliente(Long clienteId) {
        return seguroRepository.findByClienteId(clienteId);
    }
}
