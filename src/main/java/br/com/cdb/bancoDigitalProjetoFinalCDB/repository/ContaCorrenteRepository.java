package br.com.cdb.bancoDigitalProjetoFinalCDB.repository;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.ContaCorrente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente, Long> {

    List<ContaCorrente> findByClienteId(Long clienteId);
}
