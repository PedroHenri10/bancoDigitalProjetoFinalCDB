package br.com.cdb.bancoDigitalProjetoFinalCDB.service;
 
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Cartao;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.CartaoCredito;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.CartaoDebito;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Cliente;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Conta;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Seguro;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.StatusCartao;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TipoCartao;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TipoCliente;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TiposSeguro;
import br.com.cdb.bancoDigitalProjetoFinalCDB.exception.CartaoNaoEncontradoException;
import br.com.cdb.bancoDigitalProjetoFinalCDB.exception.OperacaoNaoPermitidaException;
import br.com.cdb.bancoDigitalProjetoFinalCDB.exception.SaldoInsuficienteException;
import br.com.cdb.bancoDigitalProjetoFinalCDB.repository.CartaoRepository;
import br.com.cdb.bancoDigitalProjetoFinalCDB.repository.SeguroRepository;
import br.com.cdb.bancoDigitalProjetoFinalCDB.repository.ContaRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
 
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
 
import static br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TipoCartao.CREDITO;
import static br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TipoCartao.DEBITO;
 
@Service
public class CartaoService {
 
    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private ContaRepository contaRepository;
 
    @Autowired
    private SeguroRepository seguroRepository;

    public CartaoCredito criarCartaoCredito(CartaoCredito cartaoRequest) {
        validarSenha(cartaoRequest.getSenha());

        Long numeroConta = cartaoRequest.getConta().getNumeroConta();

        Conta contaCompleta = contaRepository.findById(numeroConta)
                .orElseThrow(() -> new RuntimeException("Conta com número " + numeroConta + " não encontrada."));

        if (contaCompleta.getCliente() == null) {
            throw new IllegalStateException("A conta " + numeroConta + " não possui um cliente associado.");
        }

        cartaoRequest.setConta(contaCompleta);
        cartaoRequest.setCliente(contaCompleta.getCliente());

        boolean existe = cartaoRepository.existsByConta_NumeroContaAndTipo(contaCompleta.getNumeroConta(), CREDITO);
        if (existe) throw new OperacaoNaoPermitidaException("Cartão de crédito já existe para esta conta.");

        cartaoRequest.setTipo(CREDITO);
        cartaoRequest.setStatus(StatusCartao.INATIVO);
        cartaoRequest.setFaturaAtual(0.0);
        cartaoRequest.setLimiteCredito(definirLimiteInicial(contaCompleta.getCliente().getTipoCliente()));

        CartaoCredito salvo = cartaoRepository.save(cartaoRequest);
        criarSeguroFraudeAutomatico(salvo);
        return salvo;
    }


    public CartaoCredito adicionarGasto(Long id, double valor) {
        CartaoCredito cartao = (CartaoCredito) buscarCartaoPorId(id);
 
        if (!cartao.getStatus().equals(StatusCartao.ATIVO)) {
            throw new OperacaoNaoPermitidaException("Cartão inativo.");
        }
 
        double limite = cartao.getLimiteCredito().doubleValue();
        double faturaAtual = cartao.getFaturaAtual();
        double novoTotal = faturaAtual + valor;
 
        if (novoTotal > limite) {
            cartao.setStatus(StatusCartao.BLOQUEADO);
            cartaoRepository.save(cartao);
            throw new OperacaoNaoPermitidaException("Limite excedido. Cartão bloqueado automaticamente.");
        }
 
        cartao.setFaturaAtual(novoTotal);
        cartao.setGastosMensais(cartao.getGastosMensais() + valor);
 
        return cartaoRepository.save(cartao);
    }
    
    public String consultarFatura(Long id) {
        CartaoCredito cartao = (CartaoCredito) buscarCartaoPorId(id);
        return "Fatura atual: R$ " + cartao.getFaturaAtual() +
               " | Limite total: R$ " + cartao.getLimiteCredito() +
               " | Saldo disponível: R$ " + (cartao.getLimiteCredito().doubleValue() - cartao.getFaturaAtual()) +
               " | Taxa de utilização: R$ " + cartao.getTaxaUtilizacao();
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
 
    public CartaoCredito fecharFaturaComTaxa(Long id) {
        CartaoCredito cartao = (CartaoCredito) buscarCartaoPorId(id);
        double gasto = cartao.getFaturaAtual();
        double limite = cartao.getLimiteCredito().doubleValue();
        if (gasto >= limite * 0.8) {
            double taxa = gasto * 0.05;
            cartao.setTaxaUtilizacao(taxa);
            cartao.setFaturaAtual(gasto + taxa);
        }
        return cartaoRepository.save(cartao);
    }
    
    public CartaoCredito ajustarLimiteCredito(Long id, BigDecimal novoLimite) {
        CartaoCredito cartao = (CartaoCredito) buscarCartaoPorId(id);
        cartao.setLimiteCredito(novoLimite);
        return cartaoRepository.save(cartao);
    }

    public CartaoDebito criarCartaoDebito(CartaoDebito cartaoRequest) {
        validarSenha(cartaoRequest.getSenha());

        Long numeroConta = cartaoRequest.getConta().getNumeroConta();

        Conta contaCompleta = contaRepository.findById(numeroConta)
                .orElseThrow(() -> new RuntimeException("Conta com número " + numeroConta + " não encontrada."));

        if (contaCompleta.getCliente() == null) {
            throw new IllegalStateException("A conta " + numeroConta + " não possui um cliente associado.");
        }

        boolean existe = cartaoRepository.existsByConta_NumeroContaAndTipo(contaCompleta.getNumeroConta(), DEBITO);
        if (existe) {
            throw new OperacaoNaoPermitidaException("Cartão de débito já existe para esta conta.");
        }

        cartaoRequest.setConta(contaCompleta);
        cartaoRequest.setCliente(contaCompleta.getCliente());
        cartaoRequest.setTipo(DEBITO);
        cartaoRequest.setStatus(StatusCartao.INATIVO);
        cartaoRequest.setFaturaAtual(0.0);

        TipoCliente tipoCliente = contaCompleta.getCliente().getTipoCliente();
        int limiteDiario;
        switch (tipoCliente) {
            case COMUM:
                limiteDiario = 1000;
                break;
            case SUPER:
                limiteDiario = 5000;
                break;
            case PREMIUM:
                limiteDiario = 10000;
                break;
            default:
                throw new OperacaoNaoPermitidaException("Tipo de cliente inválido.");
        }
        cartaoRequest.setLimiteDiario(limiteDiario);

        return cartaoRepository.save(cartaoRequest);
    }


    public Cartao realizarPagamentoDebito(Long id, double valor) {
        Cartao cartao = buscarCartaoPorId(id);

        if (!cartao.getTipo().equals(TipoCartao.DEBITO)) {
            throw new OperacaoNaoPermitidaException("Cartão não é do tipo débito.");
        }

        CartaoDebito cartaoDebito = (CartaoDebito) cartao;
        Conta conta = cartaoDebito.getConta();

        if (cartaoDebito.getDataUltimoGasto() == null ||
                !cartaoDebito.getDataUltimoGasto().isEqual(LocalDate.now())) {
            cartaoDebito.setValorGastoHoje(0.0);
            cartaoDebito.setDataUltimoGasto(LocalDate.now());
        }

        double totalGastoHoje = cartaoDebito.getValorGastoHoje();

        if ((totalGastoHoje + valor) > cartaoDebito.getLimiteDiario()) {
            throw new OperacaoNaoPermitidaException("Valor excede o limite diário do cartão.");
        }

        if (conta.getSaldo() < valor) {
            throw new SaldoInsuficienteException("Saldo insuficiente.");
        }

        conta.setSaldo(conta.getSaldo() - valor);
        cartaoDebito.setValorGastoHoje(totalGastoHoje + valor);
        cartaoDebito.setDataUltimoGasto(LocalDate.now());

        return cartaoRepository.save(cartaoDebito);
    }


    public CartaoDebito ajustarLimiteDiario(Long id, int novoLimite) {
        CartaoDebito cartao = (CartaoDebito) buscarCartaoPorId(id);
        cartao.setLimiteDiario(novoLimite);
        return cartaoRepository.save(cartao);
    }
 
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
 
    public List<Cartao> listarCartoesPorConta(Conta conta) {
        return cartaoRepository.findByConta(conta);
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
        if(senha < 1000  || senha > 9999){
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
