package br.com.cdb.bancoDigitalProjetoFinalCDB.controller;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Cartao;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.CartaoCredito;
import br.com.cdb.bancoDigitalProjetoFinalCDB.service.CartaoService;
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
    public ResponseEntity<String> adicionarGasto(@PathVariable Long id, @RequestParam double valor){
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
        return ResponseEntity.ok(cartaoService.criarCartaoDebito(cartap));
    }

    @PostMapping("/debito/{id}/pagar")
    public ResponseEntity<Cartao> realizarPagamentoDebito(@PathVariable Long id, @RequestParam double valor){
        return ResponseEntity.ok(cartaoService.realizarPagamentoDebito(id, valor));
    }

    @PutMapping("/debito/{id}/limite-diario")
    public ResponseEntity<CartaoDebito> ajustarLimiteDiario(@PathVariable Long id, @RequestParam int noovoLimite){
        return ResponseEntity.ok(cartaoService.ajustarLimiteDiario(id, novoLimite));
    }



}
