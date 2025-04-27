package br.com.cdb.bancoDigitalProjetoFinalCDB.repository;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.CartaoDebito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoDebitoRepository extends JpaRepository<CartaoDebito, Long> {
}
