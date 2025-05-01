package br.com.cdb.bancoDigitalProjetoFinalCDB.Service;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Cartao;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.StatusCartao;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TipoCartao;
import br.com.cdb.bancoDigitalProjetoFinalCDB.exception.CartaoNaoEncontradoException;
import br.com.cdb.bancoDigitalProjetoFinalCDB.repository.CartaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    public Cartao criarCartaoDebito(Cartao cartao){
        cartao.setTipo(TipoCartao.DEBITO);
        cartao.setStatus(StatusCartao.ATIVO);
        return cartaoRepository.save(cartao);
    }

    public Cartao criarCartaoCredito(Cartao cartao){
        cartao.setTipo(TipoCartao.CREDITO);
        cartao.setStatus(StatusCartao.ATIVO);
        return cartaoRepository.save(cartao);
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

}
