package br.com.cdb.bancoDigitalProjetoFinalCDB.service;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Cartao;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.CartaoCredito;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Cliente;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Seguro;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.StatusCartao;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TipoCartao;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TipoCliente;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TiposSeguro;
import br.com.cdb.bancoDigitalProjetoFinalCDB.exception.CartaoNaoEncontradoException;
import br.com.cdb.bancoDigitalProjetoFinalCDB.exception.OperacaoNaoPermitidaException;
import br.com.cdb.bancoDigitalProjetoFinalCDB.repository.CartaoRepository;
import br.com.cdb.bancoDigitalProjetoFinalCDB.repository.SeguroRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TipoCartao.CREDITO;
import static br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TipoCartao.DEBITO;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private SeguroRepository seguroRepository;

    public Cartao criarCartaoDebito(CartaoCredito cartao){
        validarSenha(cartao.getSenha());

        boolean existe = cartaoRepository.existsByContaIdAndTipoCartao(cartao.getConta().getNumeroConta(), DEBITO);
        if (existe) throw new OperacaoNaoPermitidaException("Cartão de débito já existe para esta conta.");

        cartao.setTipo(TipoCartao.DEBITO);
        cartao.setStatus(StatusCartao.ATIVO);
        return cartaoRepository.save(cartao);
    }

    public CartaoCredito criarCartaoCredito(CartaoCredito cartao) {
        validarSenha(cartao.getSenha());

        boolean existe = cartaoRepository.existsByContaIdAndTipoCartao(cartao.getConta().getNumeroConta(), TipoCartao.CREDITO);
        if (existe) throw new OperacaoNaoPermitidaException("Cartão de crédito já existe para esta conta.");

        cartao.setTipo(TipoCartao.CREDITO);
        cartao.setStatus(StatusCartao.ATIVO);
        cartao.setFaturaAtual(0.0);
        cartao.setLimiteCredito(definirLimiteInicial(cartao.getConta().getCliente().getTipoCliente()));

        CartaoCredito salvo = cartaoRepository.save(cartao);
        criarSeguroFraudeAutomatico(salvo);
        return salvo;
    }

    public CartaoCredito adicionarGasto(Long id, double valor) {
        CartaoCredito cartao = (CartaoCredito) buscarCartaoPorId(id);
        if (!cartao.getStatus().equals(StatusCartao.ATIVO)) throw new OperacaoNaoPermitidaException("Cartão inativo.");
        if (cartao.getFaturaAtual() + valor > cartao.getLimiteCredito().doubleValue()) throw new OperacaoNaoPermitidaException("Limite excedido.");
        cartao.setFaturaAtual(cartao.getFaturaAtual() + valor);
        cartao.setGastosMensais(cartao.getGastosMensais() + valor);
        return cartaoRepository.save(cartao);
    }

    public CartaoCredito realizarPagamentoFatura(Long id) {
        CartaoCredito cartao = (CartaoCredito) buscarCartaoPorId(id);
        Conta conta = cartao.getConta();
        if (conta.getSaldo() < cartao.getFaturaAtual()) throw new SaldoInsuficienteException("Saldo insuficiente.");
        conta.setSaldo(conta.getSaldo() - cartao.getFaturaAtual());
        cartao.setFaturaAtual(0.0);
        cartao.setGastosMensais(0.0);
        return cartaoRepository.save(cartao);
    }

    //fechar fatuura

    public CartaoCredito ajustarLimiteCredito(Long id, BigDecimal novoLimite) {
        CartaoCredito cartao = (CartaoCredito) buscarCartaoPorId(id);
        cartao.setLimiteCredito(novoLimite);
        return cartaoRepository.save(cartao);
    }

    /*public Cartao criarCartaoDebito(Cartao cartao) {
        validarSenha(cartao.getSenha());
        boolean existe = cartaoRepository.existsByContaIdAndTipoCartao(cartao.getConta().getNumeroConta(), TipoCartao.DEBITO);
        if (existe) throw new OperacaoNaoPermitidaException("Cartão de débito já existe para esta conta.");
        cartao.setTipo(TipoCartao.DEBITO);
        cartao.setStatus(StatusCartao.ATIVO);
        return cartaoRepository.save(cartao);
    }*/

    public Cartao alterarSenha(Long id, int novaSenha) {
        validarSenha(novaSenha);
        Cartao cartao = buscarCartaoPorId(id);
        cartao.setSenha(novaSenha);
        return cartaoRepository.save(cartao);
    }

    public Cartao buscarCartaoPorId(Long id) {
        return cartaoRepository.findById(id).orElseThrow(() -> new CartaoNaoEncontradoException("Cartão não encontrado."));
    }

    public List<Cartao> listarCartoesPorCliente(Long clienteId) {
        return cartaoRepository.findByClienteId(clienteId);
    }

    public List<Cartao> listarCartoesPorConta(Long contaId) {
        return cartaoRepository.findByContaId(contaId);
    }

    public Cartao ativarCartao(Long id){
        Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new CartaoNaoEncontradoException("Cartao não encontrado."));
        cartao.setStatus(StatusCartao.ATIVO);
        return cartaoRepository.save(cartao);
    }

    public Cartao desativarCartao(Long id){
        Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new CartaoNaoEncontradoException("Cartao não encontrado."));
        cartao.setStatus(StatusCartao.INATIVO);
        return cartaoRepository.save(cartao);
    }

    private void validarSenha(int senha){
        if(senha < 1000){
            throw new OperacaoNaoPermitidaException("Senha inválida: mínimo 4 digitos");
        }
    }

    private BigDecimal definirLimiteInicial(TipoCliente tipoCliente) {
        if (tipoCliente == null) {
            throw new OperacaoNaoPermitidaException("Tipo de cliente não informado.");
        }

        switch (tipoCliente) {
            case COMUM:
                return new BigDecimal("1000.00");
            case SUPER:
                return new BigDecimal("5000.00");
            case PREMIUM:
                return new BigDecimal("10000.00");
            default:
                throw new OperacaoNaoPermitidaException("Tipo de cliente inválido.");
        }
    }

    private String gerarNumeroApoliceUnico() {
        return "APOLICE-" + UUID.randomUUID();
    }

    private void criarSeguroFraudeAutomatico(CartaoCredito cartaoCredito) {
        Seguro seguro = new Seguro();
        seguro.setNumeroApolice(gerarNumeroApoliceUnico());
        seguro.setTipo(TiposSeguro.SEGURO_FRAUDE);
        seguro.setValor(5000.0);
        seguro.setDataInicio(LocalDate.now().toString());
        seguro.setCartaoCredito(cartaoCredito);

        seguroRepository.save(seguro);
    }
}
