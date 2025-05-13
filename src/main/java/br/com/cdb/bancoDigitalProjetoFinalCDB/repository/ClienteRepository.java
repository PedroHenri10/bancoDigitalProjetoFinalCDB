package br.com.cdb.bancoDigitalProjetoFinalCDB.repository;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByCpf(String cpf);
    boolean existsByCpf(String cpf);
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
}
