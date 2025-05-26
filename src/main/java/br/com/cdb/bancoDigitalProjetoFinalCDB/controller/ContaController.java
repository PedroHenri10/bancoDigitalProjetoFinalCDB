package br.com.cdb.bancoDigitalProjetoFinalCDB.controller;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Conta;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.ContaCorrente;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.ContaPoupanca;
import br.com.cdb.bancoDigitalProjetoFinalCDB.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping("/corrente")
    public ResponseEntity<Conta> criarContaCorrente(@RequestBody ContaCorrente conta) {
        return ResponseEntity.ok(contaService.criarContaCorrente(conta));
    }

    @PostMapping("/poupanca")
    public ResponseEntity<Conta> criarContaPoupanca(@RequestBody ContaPoupanca conta) {
        return ResponseEntity.ok(contaService.criarContaPoupanca(conta));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conta> buscarConta(@PathVariable Long id) {
        return ResponseEntity.ok(contaService.buscarContaPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Conta>> listarContas() {
        return ResponseEntity.ok(contaService.listarTodasContas());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerConta(@PathVariable Long id) {
        contaService.removerConta(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/saldo")
    public ResponseEntity<Double> consultarSaldo(@PathVariable Long id) {
        return ResponseEntity.ok(contaService.consultarSaldo(id));
    }

    @PostMapping("/{id}/deposito")
    public ResponseEntity<Void> depositar(@PathVariable Long id, @RequestParam double valor) {
        contaService.realizarDeposito(id, valor);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/saque")
    public ResponseEntity<Void> sacar(@PathVariable Long id, @RequestParam double valor) {
        contaService.realizarSaque(id, valor);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/manutencao")
    public ResponseEntity<Void> aplicarManutencao(@PathVariable Long id) {
        contaService.aplicarTaxaManutencaoMensal(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/rendimentos")
    public ResponseEntity<Void> aplicarRendimento(@PathVariable Long id) {
        contaService.aplicarRendimentoMensal(id);
        return ResponseEntity.ok().build();
    }
}
