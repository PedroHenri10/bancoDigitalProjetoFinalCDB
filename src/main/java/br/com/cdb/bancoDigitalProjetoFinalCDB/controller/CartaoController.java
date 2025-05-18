package br.com.cdb.bancoDigitalProjetoFinalCDB.controller;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Cartao;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.CartaoCredito;
import br.com.cdb.bancoDigitalProjetoFinalCDB.service.CartaoService;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.CartaoDebito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

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
    public ResponseEntity<Cartao> criarCartaoDebito(@RequestBody Cartao cartao){
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

    @GetMapping("/conta/{contaId}")
    public ResponseEntity<List<Cartao>> listarPorConta(@PathVariable Long contaId) {
        return ResponseEntity.ok(cartaoService.listarCartoesPorConta(contaId));
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
