package br.com.cdb.bancoDigitalProjetoFinalCDB.controller;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Cartao;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.CartaoCredito;
import br.com.cdb.bancoDigitalProjetoFinalCDB.service.CartaoService;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.CartaoDebito;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Conta;
import br.com.cdb.bancoDigitalProjetoFinalCDB.repository.ContaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;
    
    @Autowired
    private ContaRepository contaRepository;

    @PostMapping("/credito")
    public ResponseEntity<CartaoCredito> criarCartaoCredito(@RequestBody CartaoCredito cartao){
       return ResponseEntity.ok(cartaoService.criarCartaoCredito(cartao));
    }

    @PostMapping("/credito/{id}/gasto")
    public ResponseEntity<CartaoCredito> adicionarGasto(@PathVariable Long id, @RequestParam double valor){
        return ResponseEntity.ok(cartaoService.adicionarGasto(id, valor));
    }

    @GetMapping("/credito/{id}/fatura")
    public ResponseEntity<String> consultarFatura(@PathVariable Long id){
        return ResponseEntity.ok(cartaoService.consultarFatura(id));
    }

    @PostMapping("/credito/{id}/pagar")
    public ResponseEntity<CartaoCredito> pagarFatura(@PathVariable Long id){
        return ResponseEntity.ok(cartaoService.realizarPagamentoFatura(id));
    }

    @PostMapping("/credito/{id}/fechar")
    public ResponseEntity<CartaoCredito> fecharFatura(@PathVariable Long id){
        return ResponseEntity.ok(cartaoService.fecharFaturaComTaxa(id));
    }

    @PutMapping("/credito/{id}/limite")
    public ResponseEntity<CartaoCredito> ajustarLimiteCredito(@PathVariable Long id, @RequestParam BigDecimal limite){
        return ResponseEntity.ok(cartaoService.ajustarLimiteCredito(id, limite));
    }

    @PostMapping("/debito")
    public ResponseEntity<CartaoDebito> criarCartaoDebito(@RequestBody CartaoDebito cartao){
        return ResponseEntity.ok(cartaoService.criarCartaoDebito(cartao));
    }


    @PostMapping("/debito/{id}/pagar")
    public ResponseEntity<Cartao> realizarPagamentoDebito(@PathVariable Long id, @RequestParam double valor){
        return ResponseEntity.ok(cartaoService.realizarPagamentoDebito(id, valor));
    }

    @PutMapping("/debito/{id}/limite-diario")
    public ResponseEntity<CartaoDebito> ajustarLimiteDiario(@PathVariable Long id, @RequestParam int novoLimite) {
        return ResponseEntity.ok(cartaoService.ajustarLimiteDiario(id, novoLimite));
    }

    @PutMapping("/{id}/senha")
    public ResponseEntity<Cartao> alterarSenha(@PathVariable Long id, @RequestParam int novaSenha) {
        return ResponseEntity.ok(cartaoService.alterarSenha(id, novaSenha));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cartao> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cartaoService.buscarCartaoPorId(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Cartao>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(cartaoService.listarCartoesPorCliente(clienteId));
    }
    @GetMapping("/conta/{numeroConta}")
    public ResponseEntity<List<Cartao>> listarPorConta(@PathVariable long numeroConta) {
        Conta conta = contaRepository.findByNumeroConta(numeroConta)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada"));

        return ResponseEntity.ok(cartaoService.listarCartoesPorConta(conta));
    }

    @PutMapping("/{id}/ativar")
    public ResponseEntity<Cartao> ativar(@PathVariable Long id) {
        return ResponseEntity.ok(cartaoService.ativarCartao(id));
    }

    @PutMapping("/{id}/desativar")
    public ResponseEntity<Cartao> desativar(@PathVariable Long id) {
        return ResponseEntity.ok(cartaoService.desativarCartao(id));
    }

}
