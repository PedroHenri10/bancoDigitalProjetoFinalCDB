package br.com.cdb.bancoDigitalProjetoFinalCDB.repository;

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

    @Query("SELECT DISTINCT s FROM Seguro s WHERE s.cartaoCredito IN :cartoesCredito")
    List<Seguro> findByCartaoCreditoInDistinct(@Param("cartoesCredito") List<CartaoCredito> cartoesCredito);
    List<Seguro> findByCartaoCredito_NumeroCartao(Long numeroCartao);

    boolean existsByCartaoCreditoAndTipo(CartaoCredito cartaoCredito, TiposSeguro tipo);
}
