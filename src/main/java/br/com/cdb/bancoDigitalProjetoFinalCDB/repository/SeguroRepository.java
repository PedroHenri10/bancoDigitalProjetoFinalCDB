package br.com.cdb.bancoDigitalProjetoFinalCDB.repository;

import br.com.cdb.bancoDigitalProjetoFinalCDB.dto.SeguroRespostaDTO;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.CartaoCredito;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Seguro;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TipoCartao;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TiposSeguro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeguroRepository extends JpaRepository<Seguro, Long> {

    interface SeguroClienteProjecao {
        Long getId();
        String getNumeroApolice();
        TiposSeguro getTipo();
        Double getValor();
        String getDataInicio();
        InfoCartaoCredito getCartaoCredito();

        interface InfoCartaoCredito {
            Long getNumeroCartao();
        }
    }

    @Query("SELECT new br.com.cdb.bancoDigitalProjetoFinalCDB.dto.SeguroRespostaDTO(" +
            "s.id, s.numeroApolice, s.tipo, s.valor, s.dataInicio, s.cartaoCredito.numeroCartao) " +
            "FROM Seguro s WHERE s.cartaoCredito.cliente.id = :clienteId")
    List<SeguroRespostaDTO> buscarSegurosDTOPorIdCliente(@Param("clienteId") Long clienteId);
    List<Seguro> findByCartaoCredito_NumeroCartao(Long numeroCartao);

    boolean existsByCartaoCreditoAndTipo(CartaoCredito cartaoCredito, TiposSeguro tipo);
}
