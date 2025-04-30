package br.com.cdb.bancoDigitalProjetoFinalCDB.Service;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Cartao;
import br.com.cdb.bancoDigitalProjetoFinalCDB.repository.CartaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    public Cartao criarCartaoDebito(Cartao cartao){
        cartao.setTipo("DEBITO");
        cartao.setStatus(true);
        return cartaoRepository.save(cartao);
    }
}
