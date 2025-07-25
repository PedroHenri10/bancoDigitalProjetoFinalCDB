package br.com.cdb.bancoDigitalProjetoFinalCDB.service;

import br.com.cdb.bancoDigitalProjetoFinalCDB.dto.SeguroRespostaDTO;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Cartao;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.CartaoCredito;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Seguro;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TipoCliente;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TiposSeguro;
import br.com.cdb.bancoDigitalProjetoFinalCDB.exception.CartaoNaoEncontradoException;
import br.com.cdb.bancoDigitalProjetoFinalCDB.exception.OperacaoNaoPermitidaException;
import br.com.cdb.bancoDigitalProjetoFinalCDB.exception.SeguroNaoEncontradoException;
import br.com.cdb.bancoDigitalProjetoFinalCDB.repository.CartaoRepository;
import br.com.cdb.bancoDigitalProjetoFinalCDB.repository.SeguroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SeguroService {

    @Autowired
    private SeguroRepository seguroRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    public Seguro contratarSeguroViagem(Long cartaoId) {
        CartaoCredito cartaoCredito = buscarEValidarCartaoCredito(cartaoId);

        if (seguroRepository.existsByCartaoCreditoAndTipo(cartaoCredito, TiposSeguro.SEGURO_VIAGEM)) {
            throw new OperacaoNaoPermitidaException("Este cartão já possui um seguro viagem.");
        }

        TipoCliente tipoCliente = cartaoCredito.getCliente().getTipoCliente();
        double valorSeguro = switch (tipoCliente) {
            case PREMIUM -> 0.0;
            case SUPER, COMUM -> 50.0;
        };

        Seguro seguro = new Seguro();
        seguro.setTipo(TiposSeguro.SEGURO_VIAGEM);
        seguro.setValor(valorSeguro);
        seguro.setNumeroApolice(UUID.randomUUID().toString());
        seguro.setDataInicio(LocalDate.now().toString());
        seguro.setCartaoCredito(cartaoCredito);

        return seguroRepository.save(seguro);
    }

    private CartaoCredito buscarEValidarCartaoCredito(Long cartaoId) {
        Cartao cartao = cartaoRepository.findById(cartaoId)
                .orElseThrow(() -> new CartaoNaoEncontradoException("Cartão com ID " + cartaoId + " não encontrado."));
        if (!(cartao instanceof CartaoCredito)) {
            throw new OperacaoNaoPermitidaException("A operação de seguro só é permitida para cartões de crédito.");
        }
        if (cartao.getCliente() == null) {
            throw new OperacaoNaoPermitidaException("Cartão com ID " + cartaoId + " não possui um cliente associado.");
        }
        return (CartaoCredito) cartao;
    }

    public Seguro cancelarSeguro(Long id) {
        Seguro seguro = seguroRepository.findById(id)
                .orElseThrow(() -> new SeguroNaoEncontradoException("Seguro com ID " + id + " não encontrado."));

        if (seguro.getTipo() == TiposSeguro.SEGURO_FRAUDE) {
            throw new OperacaoNaoPermitidaException("O seguro contra fraude é obrigatório e não pode ser cancelado.");
        }

        seguroRepository.delete(seguro);
        return seguro;
    }

    public Seguro buscarSeguroPorId(Long id) {
        return seguroRepository.findById(id)
                .orElseThrow(() -> new SeguroNaoEncontradoException("Seguro com ID " + id + " não encontrado."));
    }

    public List<SeguroRespostaDTO> listarSegurosPorCliente(Long clienteId) {
        return seguroRepository.buscarSegurosDTOPorIdCliente(clienteId);
    }

    public SeguroRespostaDTO converterParaDTO(Seguro seguro) {
        if (seguro == null) return null;
        return new SeguroRespostaDTO(
                seguro.getId(),
                seguro.getNumeroApolice(),
                seguro.getTipo(),
                seguro.getValor(),
                seguro.getDataInicio(),
                seguro.getCartaoCredito() != null ? seguro.getCartaoCredito().getNumeroCartao() : null
        );
    }
}