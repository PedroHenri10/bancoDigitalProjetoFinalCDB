package br.com.cdb.bancoDigitalProjetoFinalCDB.repository;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Cartao;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Conta;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.enums.TipoCartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {
    List<Cartao> findByClienteId(Long clienteId);      
    List<Cartao> findByConta(Conta conta);
    List<Cartao> findByConta_NumeroConta(Long numeroConta);
    List<Cartao> findAllByConta_NumeroConta(Long numeroConta);
    boolean existsByConta_NumeroContaAndTipo(Long numeroConta, TipoCartao tipoCartao);

    @Query("SELECT c.numeroCartao FROM Cartao c WHERE c.cliente.id = :clienteId AND TYPE(c) = CartaoCredito")
    List<Long> findNumeroCartaoCreditoByClienteId(@Param("clienteId") Long clienteId);

    @Query("SELECT c FROM Cartao c WHERE c.numeroCartao = :id")
    Optional<Cartao> findByIdSimple(@Param("id") Long id);

}
