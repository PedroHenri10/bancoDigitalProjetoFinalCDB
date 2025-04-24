package br.com.cdb.bancoDigitalProjetoFinalCDB.repository;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long>{
    List<Cartao> findByClienteId(Long clienteId);
    List<Cartao> findByContaId(Long contaId);
}
