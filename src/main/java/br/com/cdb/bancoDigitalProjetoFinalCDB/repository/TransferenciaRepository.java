package br.com.cdb.bancoDigitalProjetoFinalCDB.repository;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {

    List<Transferencia> findByContaOrigemId(Long contaId);

    List<Transferencia> findByContaDestinoId(Long contaId);
}