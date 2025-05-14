package br.com.cdb.bancoDigitalProjetoFinalCDB.controller;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Transferencia;
import br.com.cdb.bancoDigitalProjetoFinalCDB.Service.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transferencias")
public class TransferenciaController {

    @Autowired
    private TransferenciaService transferenciaService;

    @PostMapping
    public ResponseEntity<Transferencia> realizarPix(
            @RequestParam Long origemId,
            @RequestParam Long destinoId,
            @RequestParam BigDecimal valor,
            @RequestParam String descricao) {

        Transferencia t = transferenciaService.realizarTransferenciaPix(origemId, destinoId, valor, descricao);
        return ResponseEntity.ok(t);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transferencia> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(transferenciaService.buscarTransferenciaPorId(id));
    }

    @GetMapping("/conta/{contaId}")
    public ResponseEntity<List<Transferencia>> listarPorConta(@PathVariable Long contaId) {
        return ResponseEntity.ok(transferenciaService.listarTransferenciasPorConta(contaId));
    }
}
